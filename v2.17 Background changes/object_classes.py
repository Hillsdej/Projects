import pygame
from tileClass import Tile
from random import randint

class Character(pygame.Rect):

    width, height = 32,32

    def __init__(self, x, y):

        self.tx, self.ty = None,None
        pygame.Rect.__init__(self, x, y, Character.width, Character.height)
        
    def __str__(self):
        return str(self.get_number())

    def set_target(self, next_tile):
        if self.tx == None and self.ty == None:
            self.tx = next_tile.x
            self.ty = next_tile.y

    def get_number(self):

        return ((self.x // self.width) + Tile.H) + ((self.y // self.height)* Tile.V)

    def get_tile(self):

        return Tile.get_tile(self.get_number())

    def rotate(self, direction ,original_img):

        if direction == "n":
            if self.direction != "n":
                self.direction = "n"
                south = pygame.transform.rotate(original_img, 90)
                self.img = pygame.transform.flip(south, False,True)

        if direction == "s":
            if self.direction != "s":
                self.direction = "s"
                self.img = pygame.transform.rotate(original_img, 90)

        if direction == "e":
            if self.direction != "e":
                self.direction = "e"
                self.img = pygame.transform.flip(original_img, True, False)
            
        if direction == "w":
            if self.direction != "w":
                self.direction = "w"
                self.img = original_img
    



class Enemy(Character):

    List = []
    spawn_tiles = (12,15,168)
    original_img = pygame.image.load("images/alien.png")                                     

    def __init__(self, x, y):

        self.direction = "w"
        self.img = Enemy.original_img
        Character.__init__(self, x, y)
        Enemy.List.append(self)

    @staticmethod
    def draw_enemies(screen):
        for enemy in Enemy.List:
            screen.blit(enemy.img,(enemy.x, enemy.y))

    @staticmethod
    def movement():
        for enemy in Enemy.List:
            if enemy.tx != None and enemy.ty != None:

                X = enemy.x - enemy.tx
                Y = enemy.y - enemy.ty

                vel = 4

                if X < 0: # right
                    enemy.x += vel
                    enemy.rotate("e",Enemy.original_img)
                    
                elif X > 0:# left
                    enemy.x -= vel
                    enemy.rotate("w",Enemy.original_img)

                if Y > 0: #up
                    enemy.y -= vel
                    enemy.rotate("n",Enemy.original_img)
                    
                elif Y < 0:#down
                    enemy.y += vel
                    enemy.rotate("s",Enemy.original_img)

                if X == 0 and Y == 0:
                    enemy.tx, enemy.ty = None, None

    @staticmethod
    def spawn(total_frames, FPS):
        if total_frames % (FPS * 3) == 0:
            r = randint(0,len(Enemy.spawn_tiles)-1)
            tile_num = Enemy.spawn_tiles[r]
            spawn_node = Tile.get_tile(tile_num)
            Enemy(spawn_node.x, spawn_node.y)
            

class Player(Character):

    original_img = None

    def __init__(self, x, y):

        self.direction = "w"
        self.img = pygame.image.load('images/spaceship_w.png')
        
        Character.__init__(self, x, y)


    def movement(self):

       if self.tx != None and self.ty != None:

            X = self.x - self.tx
            Y = self.y - self.ty

            vel = 8

            if X < 0: # right
                self.x += vel
            elif X > 0:# left
                self.x -= vel

            if Y > 0: #up
                self.y -= vel
            elif Y < 0:#down
                self.y += vel

            if X == 0 and Y == 0:
                self.tx, self.ty = None, None

    def draw(self, screen):
        screen.blit(self.img, (self.x, self.y))

    def rotate(self, direction):

        path = 'images/spaceship_'
        png = '.png'
        
        if direction == "n":
            if self.direction != "n":
                self.direction = "n"
                self.img = pygame.image.load(path + self.direction + png)

        if direction == "s":
            if self.direction != "s":
                self.direction = "s"
                self.img = pygame.image.load(path + self.direction + png)

        if direction == "e":
            if self.direction != "e":
                self.direction = "e"
                self.img = pygame.image.load(path + self.direction + png)
            
        if direction == "w":
            if self.direction != "w":
                self.direction = "w"
                self.img = pygame.image.load(path + self.direction + png)
        
    
