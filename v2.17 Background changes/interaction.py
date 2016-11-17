import pygame,sys
from tileClass import Tile
from object_classes import *

def interaction(screen, player):

    Mpos = pygame.mouse.get_pos()
    Mx = Mpos[0] // Tile.width
    My = Mpos[1] // Tile.height
    
    for event in pygame.event.get():
        
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()

        if event.type == pygame.MOUSEBUTTONDOWN:
            for tile in Tile.List:
                if tile.x == (Mx * Tile.width) and tile.y == (My * Tile.width):
                    tile.type = 'solid'
                    tile.walkable = False
                    break

    keys = pygame.key.get_pressed()
    

    if keys[pygame.K_w]: # North
        future_tile_number = player.get_number() - Tile.V
        if future_tile_number in range(1, Tile.total_tiles + 1):
            future_tile = Tile.get_tile(future_tile_number)
            if future_tile.walkable:
                player.set_target(future_tile)
                player.rotate('n')
                #player.y -= player.height                   

    if keys[pygame.K_s]: # South
        future_tile_number = player.get_number() + Tile.V
        if future_tile_number in range(1, Tile.total_tiles + 1):
            future_tile = Tile.get_tile(future_tile_number)
            if future_tile.walkable:
                player.set_target(future_tile)
                player.rotate('s')
                #player.y += player.height 

    if keys[pygame.K_a]: # West
        future_tile_number = player.get_number() - Tile.H
        if future_tile_number in range(1, Tile.total_tiles + 1):
            future_tile = Tile.get_tile(future_tile_number)
            if future_tile.walkable:
                player.set_target(future_tile)
                player.rotate('w')
                
                #player.x -= player.width 

    if keys[pygame.K_d]: # East
        future_tile_number = player.get_number() + Tile.H

        if future_tile_number in range(1, Tile.total_tiles + 1):
            future_tile = Tile.get_tile(future_tile_number)
            if future_tile.walkable:
                player.set_target(future_tile)
                player.rotate('e')
                #player.x += player.width 

                    
                
