#Question 1
print("Question 1")

rArray = []
sentence = "this is just a test"
sArray = sentence.split()

def reverse2(x):
    
    if x >= 0:
        rArray.append(sArray[x])
        reverse2(x-1)
    else:    
        reverse = " ".join(rArray)
        print(reverse)
    
reverse2(len(sArray)-1)

    

#Question 2
print ("Question 2")

def primeCheck(x,y):
    
    #y must be 1 less than x for this to work properly
    
    if y <= 1:
        print("x is a prime")    
    else:
        if x % y == 0:
            print("x is not prime")
        else:
            primeCheck(x,y-1)

primeCheck(11,10)


#Question 3
print("Question 3")

word = "evacuation"
wordList = list(word)

v = "aeiou"
x = 0

def vowelRemoval(wordStep,wordLength):
    
    if wordStep >= wordLength:
        return wordList
    elif wordList[wordStep] in v:
        wordList.remove(wordList[wordStep])
        return vowelRemoval(wordStep,wordLength-1)
    else:
        return vowelRemoval(wordStep+1,wordLength)
        


print(vowelRemoval(0,len(word)))
        