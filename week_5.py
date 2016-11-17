#Question 1

#Tests
#seq = [1,2,3,4,1,5,1,6,7]
#seq = [5,3,2,4,6,7,3,9]
seq = [5,4,3,2,1,6,7,8,5,6,8,9,13,22]

def largest_seq(seq):
    
    """
    function that takes in an array of numbers and
    outputs the sub sequence of ascending numbers of maximum length
    """ 
    #Set positional variable to reference index when iterating through sequence
    x = 0
    
    #Set counter variable which will increment for the length of a potential substring
    counter = 1
    
    #Create empty list to store counter variables for reference later
    counterList = [] 
    
    #Iterate through sequence and increment counter
    for x in range(0,len(seq)-1):
        
        #Condition to check if current element is less than the next
        if seq[x] < seq[x+1]:
            
            counter += 1
            x += 1
            
        else:
            
            counterList.append(counter)
            #reset counter
            counter = 1
            x += 1
           
    counterList.append(counter)
    print("this is the counterList: " + str(counterList))
    
    #set another positional variable for referencing index in counterList
    y = 0
    
    #set bounds for extracting sub_sequence
    z = 0
    a = 0 
    
    #Find the counter of max value
    highestCounter = max(counterList)
    
    #iterate through the counter list to find the position of the maximum element
    
    while y in range(0,len(counterList)-1):
        
        #Condition to check if the current element is the maximum element
        if counterList[y] == highestCounter:
            
            #If the maximum element is in position 0 then return the sequence from 0 up until the value stored in the counterList at index 0
            if y == 0:
                print("this is the sub_sequence: " + str(seq[0:counterList[y]]))
                break
            elif y == 1:
                #If the maximum element is at position 1, start from the previous elements value and finish at the current elements value
                print("this is the sub_sequence: " + str(seq[counterList[y-1]:counterList[y]]))
                break
            else:
                #if the maximum element is at position 2 or more, find the sum of all the previous elements up to but not including the current element to start
                #finish at the sum of all the previous elements including the current element
                a = sum(counterList[0:y])
                z = sum(counterList[0:y+1])
                print("this is the sub_sequence: " + str(seq[a:z]))
                break
        else:
            y +=1
    
    #If the maximum element is the last element in the counterList
    if y == len(counterList)-1:
        a = sum(counterList[0:y])
        z = sum(counterList[0:y+1])
        print("this is the sub_sequence: " + str(seq[a:z]))
            
largest_seq(seq)
