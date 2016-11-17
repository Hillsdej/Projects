#Question 1
print("Question 1")

a = int(input("a: "))
b = int(input("b: "))
c = int(input("c: "))
d = int(input("d: "))

divisionAB = (a/b)
divisionCD = (c/d)

if divisionAB > divisionCD:
    print(divisionAB)
else:
    print(divisionCD)


#Question 2
print("Question 2")

#get user's input for position of point
px = input("point x: ")
px = int(px)

py = input("point y: ")
py = int(py)

ax = 3
ay = 4

bx = 0
by = 5

cx = 6
cy = 9

lowestVx = 0
lowestVy = 0
highestVx = 0
highestVy = 0

# conditionals to find lowest X vertice
if ax < bx and ax < cx:
    lowestVx = ax
elif bx < ax and bx < cx:
    lowestVx = bx
else:
    lowestVx = cx

#conditionals to find lowest Y vertice
if ay < by and ay < cy:
    lowestVy = ay
elif by < ay and by < cy:
    lowestVy = by
else:
    lowestVy = cy

#conditionals to find highest X vertice
if ax > bx and ax > cx:
    highestVx = ax
elif bx > ax and bx > cx:
    highestVx = bx
else:
    highestVx = cx

#conditionals to find highest Y vertice
if ay > by and ay > cy:
    highestVy = ay
elif by > ay and by > cy:
    highestVy = by
else:
    highestVy = cy

#check location of the x point
if px < lowestVx:
    print("left")
elif px > highestVx:
    print("right")
else:
    print("position undefined")

#check location of the y point
if py < lowestVy:
    print("bottom")
elif py > highestVy:
    print("top")
else:
    print("position undefined")

#Question 3
print("Question 3")

def func(x):
    
    #takes the input x
    
    if x < -2:
        print(x**2 + (4*x) + 4)
    elif x == 0:
        print(0)
    else:
        print(x**2 + (5*x))

func(2)


#Question 4
print("Question 4")

def delSubStr (b,l):
    
    originalStr = input ("Word: ")
    original_list = list(originalStr)
    print(original_list)
    
    for num in range(0,l):
        original_list.pop(b)
    
    newStr = " ".join(original_list)
    print (newStr)
    
delSubStr(3,2)


#Question 5
print("Question 5")

def date(d,m,y):
    
    diy = 365
    dim = [31,28,31,30,31,30,31,31,30,31,30,31]
    daysPast = sum(dim[0:m-1]) + (d-1)
    #daysPast doesnt include current day
    
    days2go = diy - daysPast
    
    #check for leap year
    
    if y % 4 == 0:
        #this is a leap year
        daysPast += 1
    elif y % 100 == 0 and y % 400 == 0:
        #this is a leap year
        daysPast += 1
    else:
        #this is not a leap year
        pass
   
    print(daysPast)
    print(days2go)
    
date(25,9,2016)

#Question 6
print("Question 6")

array = []

for num in range (0,6):
    x = input("Choose an integer to add to the list: ")
    x = int(x)
    array.append(x)

print(array)
minimumNum = min(array)

print("The lowest number in the list: " + str(minimumNum))
print("index: " + str(array.index(minimumNum)))

maximumNum = max(array)

print("The highest number in the list: " + str(maximumNum))
print("index: " + str(array.index(maximumNum)))