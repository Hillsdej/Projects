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

            #North
            if event.key == pygame.K_w: 
                future_tile_number = player.get_number()- Tile.V

                if Tile.get_tile(future_tile_number).walkable:
                    player.y -= player.height

            #South    
            if event.key == pygame.K_s: 
                future_tile_number = player.get_number() + Tile.V

                if Tile.get_tile(future_tile_number).walkable:
                    player.y += player.height

            #West
            if event.key == pygame.K_a: 
                future_tile_number = player.get_number()- Tile.H

                if Tile.get_tile(future_tile_number).walkable:
                    player.x -= player.width

            #East
            if event.key == pygame.K_d: 
                future_tile_number = player.get_number()+ Tile.H

                if Tile.get_tile(future_tile_number).walkable:
                    player.x += player.width


                
            
