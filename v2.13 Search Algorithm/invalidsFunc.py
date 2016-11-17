def invalids_func():

    invalids = []

    #border
    
    #1 - 30
    top_row = 0
    for i in range(0,30):
        top_row += 1
        invalids.append(top_row)

    #571 - 600
    bottom_row = 570
    for i in range(0,30):
        bottom_row +=1
        invalids.append(bottom_row)
        
    #31 - 541 step = 30
    left_column = 1
    for i in range(0,540,30):
        left_column += 30
        invalids.append(left_column)
        
    #60 - 570 step = 30   
    right_column = 30
    for i in range(0,570,30):
        right_column += 30
        invalids.append(right_column)

    return(invalids)
    
invalids_func()
