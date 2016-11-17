#Question 1
print("Question 1")
from math import sqrt

def perfect_square(num):
    
    lower_root = int(sqrt(num))
    upper_root = lower_root + 1
    lower_square = lower_root * lower_root
    upper_square = upper_root * upper_root
    
    if upper_square >= num:
        print("the closest perfect square is " + str(lower_root))
    else:
        print("the closest perfect square is " + str(upper_root))
        
perfect_square(63)