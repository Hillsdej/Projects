#include <arpa/inet.h>

#include <netdb.h>
#include <netinet/in.h>
#include <unistd.h>
#include <iostream>
#include <cstring>
#include <stdlib.h>
#include <stack>
#include <string>
#include <sstream>
#include <stdio.h>
#include <string.h>

#define MAX_MSG 100
#define LINE_ARRAY_SIZE (MAX_MSG+1)

using namespace std;

int main()
{
  int listenSocket, connectSocket, i;
  
  stack<int> rpnStack; //make stack to store numbers
  string number; 
  int char_out;
  
  unsigned short int listenPort;
  socklen_t clientAddressLength;
  struct sockaddr_in clientAddress, serverAddress;
  char line[LINE_ARRAY_SIZE];

  cout << "Enter port number to listen on (between 1500 and 65000): ";
  cin >> listenPort;
  
  listenSocket = socket(AF_INET, SOCK_STREAM, 0);
  if (listenSocket < 0) {
    cerr << "cannot create listen socket";
    exit(1);
  }
  
  serverAddress.sin_family = AF_INET;
  serverAddress.sin_addr.s_addr = htonl(INADDR_ANY);
  serverAddress.sin_port = htons(listenPort);
  
  if (bind(listenSocket,
           (struct sockaddr *) &serverAddress,
           sizeof(serverAddress)) < 0) {
    cerr << "cannot bind socket";
    exit(1);
  }

 
  listen(listenSocket, 5);
  
  while (1) {
    cout << "Waiting for TCP connection on port " << listenPort << " ...\n";
    clientAddressLength = sizeof(clientAddress);
    connectSocket = accept(listenSocket,
                           (struct sockaddr *) &clientAddress,
                           &clientAddressLength);
    if (connectSocket < 0) {
      cerr << "cannot accept connection ";
      exit(1);
    }
    
    cout << "  connected to " << inet_ntoa(clientAddress.sin_addr);

    cout << ":" << ntohs(clientAddress.sin_port) << "\n";
    
    memset(line, 0x0, LINE_ARRAY_SIZE);
    while (recv(connectSocket, line, MAX_MSG, 0) > 0) {
      cout << "  --  " << line << "\n";

      for (i = 0; line[i] != '\0'; i++){
        //iterate through the input one at a time
        number = line[i];
        if ((number=="+") || (number=="-")||(number=="*")||(number=="/")){
          //number is a calc, so pop off top 2 numbers and perform calculation
          int a = rpnStack.top();
          rpnStack.pop();
          int b = rpnStack.top();
          rpnStack.pop();
          switch (number[0]) {
            //determine op & push result of calc to stack
            case '+': rpnStack.push(a+b); break;
            case '-': rpnStack.push(b-a); break;
            case '*': rpnStack.push(b*a); break;
            case '/': rpnStack.push(b/a); break;
            }
            
        }else{
              //must be a number, so add to stack
              int n;
              istringstream(number) >> n;
              rpnStack.push(n);
              }
        }
        if (rpnStack.size()){
          char_out = rpnStack.top();
          //this stores final result in line, which is returned to client
          sprintf(line,"%i",char_out);
      }

      // Send RPN result back to client through line.
      if (send(connectSocket, line, strlen(line) + 1, 0) < 0)
        cerr << "Error: cannot send modified data";

      memset(line, 0x0, LINE_ARRAY_SIZE);  // set line to all zeroes
    }
  }
}
