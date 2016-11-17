import pygame, sys, Func
from tileClass import Tile
from object_classes import *
from interaction import interaction
from invalidsFunc import *
from A_Star import A_Star

pygame.init()
pygame.font.init()

invalids = tuple(invalids_func())

screen = pygame.display.set_mode((960,640))

for y in range(0, screen.get_height(), 32):
    for x in range(0, screen.get_width(), 32):
        if Tile.total_tiles in invalids:
            Tile(x, y, 'solid')
        else:
            Tile(x, y, 'empty')
            
#set time
clock = pygame.time.Clock()
FPS = 20
total_frames = 0

enemy1 = Enemy(320,352)
player = Player(224,128)

while True:
    
    screen.fill([0,0,0])
    A_Star(screen, player,total_frames,FPS)
    interaction(screen, player)
    Tile.draw_tiles(screen)
    player.draw(screen)
    Enemy.draw_enemies(screen)

    pygame.display.flip()
    clock.tick(FPS)
    total_frames += 1
    
    
