import pygame
from tileClass import Tile

class Character(pygame.Rect):
    width = 32
    height = 32

    def __init__(self,x,y):
        pygame.Rect.__init__(self,x,y,Character.width,Character.height)

    def __str__(self):
        return str(self.get_number())

    #returns the number of the tile the character is on
    def get_number(self):
        return ((self.x//self.width) + Tile.H)+ ((self.y//self.height)*Tile.V)


    #returns the tile that the character is on
    def get_tile(self):
        return Tile.get_tile(self.get_number())


class Enemy(Character):

    List = []

    def __init__(self,x,y):
        Character.__init__(self,x,y)
        Enemy.List.append(self)

    @staticmethod
    def draw_enemies(screen):
        for enemy in Enemy.List:
            pygame.draw.rect(screen, [250,0,0],enemy)

class Player(Character):

    def __init__(self,x,y):

        Character.__init__(self,x,y)

    def draw(self,screen):
        r = self.width // 2
        pygame.draw.circle(screen, [0,255,00],(self.x+r,self.y+r),r)
        
    
