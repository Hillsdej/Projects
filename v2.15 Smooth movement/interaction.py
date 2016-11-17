import pygame,sys
from tileClass import Tile

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

        if event.type == pygame.KEYDOWN:

            if event.key == pygame.K_w: # North
                future_tile_number = player.get_number() - Tile.V
                if future_tile_number in range(1, Tile.total_tiles + 1):
                    if Tile.get_tile(future_tile_number).walkable:
                        player.y -= player.height                   

            if event.key == pygame.K_s: # South
                future_tile_number = player.get_number() + Tile.V
                if future_tile_number in range(1, Tile.total_tiles + 1):

                    if Tile.get_tile(future_tile_number).walkable:
                        player.y += player.height 

            if event.key == pygame.K_a: # West
                future_tile_number = player.get_number() - Tile.H

                if future_tile_number in range(1, Tile.total_tiles + 1):
                    if Tile.get_tile(future_tile_number).walkable:
                        player.x -= player.width 

            if event.key == pygame.K_d: # East
                future_tile_number = player.get_number() + Tile.H

                if future_tile_number in range(1, Tile.total_tiles + 1):
                    if Tile.get_tile(future_tile_number).walkable:
                        player.x += player.width 

                    
                
