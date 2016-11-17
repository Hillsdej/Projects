import pygame, sys, Func
from tileClass import Tile
from invalidsFunc import *
from object_classes import *

pygame.init()
pygame.font.init()

#imported invalids must be converted from list to tuple type
invalids = tuple(invalids_func())


#screen size will be 960w, 640h
screen = pygame.display.set_mode((960,640))

#step 32 as each tile is 32*32 pixels
for y in range(0,screen.get_height(), 32):
    for x in range(0,screen.get_width(), 32):
        if Tile.total_tiles in invalids:
            Tile(x,y,'solid')
        else:
            Tile(x,y,'empty')
            

#set time
clock = pygame.time.Clock()
FPS = 24
total_frames = 0

enemy1 = Enemy(320,352)
player = Player(640,480)

#main game loop
while True:

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            
            pygame.quit()
            sys.exit()

    Tile.draw_tiles(screen)
    player.draw(screen)
    Enemy.draw_enemies(screen)

    pygame.display.flip()
    clock.tick(FPS)
    total_frames += 1
    
    
