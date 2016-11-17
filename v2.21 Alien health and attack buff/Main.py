import pygame, sys, Func
from tileClass import Tile
from object_classes import *
from interaction import interaction
from invalidsFunc import *
from A_Star import A_Star
from time import sleep 

pygame.init()
pygame.font.init()
pygame.mixer.init()

pygame.mixer.music.load("audio/halo_themes.wav")
pygame.mixer.music.play(-1)

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
FPS = 24
total_frames = 0

background = pygame.image.load('images/Background.png')
player = Player(64,128)


while player.health > 1:
    
    screen.blit(background,(0,0))

    Enemy.spawn(total_frames,FPS)
    Enemy.update(screen,player)

    player.movement()

    Bullet.super_massive_jumbo_loop(screen)

    A_Star(screen, player, total_frames, FPS)
    interaction(screen, player)
    player.draw(screen)

    Func.text_to_screen(screen,"Health {0}".format(player.health),0,0)


   

    pygame.display.flip()
    clock.tick(FPS)
    total_frames += 1

    if player.health <= 0:
        sleep(2)
        screen.blit(pygame.image.load("images/End.png"),(0,0))
        pygame.display.update()
        break

sleep(4)
        
    
    
