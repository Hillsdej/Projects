#Question 1
print("Question 1")
"""
could import random
random integer for index

"""
import random

def random_sort():
    
    array = []
    
    #generate random numbers for List
    
    for num in range(0,10):
        x = random.randint(0,100)
        array.append(x)
        
    print("This is the original array: ")
    print(array)

    arrayLength = len(array)
    
    associationA = [0,1,2,3,4]
    assosciationB = [5,6,7,8,9]
    
    
    for num in range(0,arrayLength):
        z = random.randint(0,arrayLength-1)
        a = random.choice(associationA)
        b = random.choice(assosciationB)
        array[a],array[z] = array[z], array[a]
        array[0],array[b] = array[b], array[0]
        
    
    print("This is the new array: ")
    
    print(array)

random_sort()

#Question 2 
print("Question 2")

"""
a factorial is the multiplication of its descending natural numbers
e.g. 4! == 4*3*2*1 == 24
"""
#Reference material http://www.purplemath.com/modules/factzero.htm


def zeroTrail(f):
    
    #get a list of the factors
    factorials = []
    for num in range(0,f):
        factorials.append(f)
        f -= 1
    
    total = 1
    
    #iterate through the list and multiply to get the factorial number
    for num in factorials:
        total *= num
    
    #print(total)
    
    #create new list to store how many 5's in the factorial total 
    store = []
    
    y = len(factorials) // 5
    store.append(y)
    
    while True:
        y = y // 5
        if y < 1:
            break
        else:
            store.append(y)
    
    
    
    #print(store)
    totalZero = sum(store[0:len(store)]) 
    
    print("The number of trailing zero's in this factorial number is: " + str(totalZero))   
    
zeroTrail(4617)
    

#Question 3

"""
a. Does your algorithm have defined inputs and outputs?
    
    for Question 1 they are defined but I could increase the range of the numbers used in the random generator
    for Question 2 there the input is user defined

b. Can you guarantee that it terminates?
    
    yes 
    
c. Is it specified in a clear and concise manner?

    yes
    
d. Does your algorithm produce the correct result for all instances?

    yes
"""
