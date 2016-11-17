import pygame
from tileClass import Tile
from random import randint

class Character(pygame.Rect):

    width, height = 32,32

    def __init__(self, x, y):

        pygame.Rect.__init__(self, x, y, Character.width, Character.height)

    def __str__(self):
        return str(self.get_number())

    def get_number(self):

        return ((self.x // self.width) + Tile.H) + ((self.y // self.height)* Tile.V)

    def get_tile(self):

        return Tile.get_tile(self.get_number())


class Enemy(Character):

    List = []
    spawn_tiles = (12,15,168)

    def __init__(self, x, y):

        Character.__init__(self, x, y)
        Enemy.List.append(self)

    @staticmethod
    def draw_enemies(screen):
        for enemy in Enemy.List:
            pygame.draw.rect(screen, [250,0,0],enemy)

    @staticmethod
    def spawn(total_frames,FPS):
        if total_frames % FPS // 3 == 0:
            r = randint(0,len(Enemy.spawn_tiles)-1)
            tile_num = Enemy.spawn_tiles[r]
            spawn_node = Tile.get_tile(tile_num)
            Enemy(spawn_node.x, spawn_node.y)
            

class Player(Character):

    def __init__(self, x, y):

        Character.__init__(self, x, y)

    def draw(self, screen):
        r = self.width // 2
        pygame.draw.circle(screen, [0,255,00],(self.x + r, self.y + r),r)
        
    
