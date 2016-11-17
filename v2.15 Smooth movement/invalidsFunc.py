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

    blockA = 335
    for i in range (0,4):
        blockA += 1
        invalids.append(blockA)

    blockA2 = 365
    for i in range (0,4):
        blockA2 += 1
        invalids.append(blockA2)

    blockA3 = 395
    for i in range (0,4):
        blockA3 += 1
        invalids.append(blockA3)

    volcanoA = 167
    for i in range (0,7):
        volcanoA += 1
        invalids.append(volcanoA)

    volcanoA2 = 347
    for i in range(0,7):
        volcanoA2 += 1
        invalids.append(volcanoA2)

    volcanoA3 = 168
    for i in range(0,5):
        volcanoA3 += 30
        invalids.append(volcanoA3)

    volcanoA4 = 175
    for i in range(0,5):
        volcanoA4 += 30
        invalids.append(volcanoA4)

    invalids.append(66)
    invalids.append(94)
    invalids.append(95)
    invalids.append(96)
    invalids.append(125)
    invalids.append(93)
    
    invalids.append(40)
    invalids.append(71)
    invalids.append(72)
    invalids.append(75)
    invalids.append(76)
    invalids.append(47)
    
    invalids.append(552)
    invalids.append(523)
    invalids.append(524)
    invalids.append(528)
    invalids.append(629)
    invalids.append(560)

    invalids.append(529)
    invalids.append(355)
    invalids.append(175)
         
    return(invalids)
    
invalids_func()
