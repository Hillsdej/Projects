import pygame, Func


class Tile(pygame.Rect):
    
    #stores a list of the tiles we create
    List = [] 

    width = 32
    height = 32
    H = 1
    V = 10
    total_tiles = 1

    def __init__(self, x, y, Type):

        self.type = Type
        self.number = Tile.total_tiles #gives each tile an individual number
        Tile.total_tiles += 1

        if Type == "empty":
            self.walkable = True
        else:
            self.walkable = False

        pygame.Rect.__init__(self,(x,y),(Tile.width, Tile.height))

        Tile.List.append(self)

    @staticmethod
    def get_tile(number):
        for tile in Tile.List:
            if tile.number == number:
                return tile

    @staticmethod
    def draw_tiles(screen):
        for tile in Tile.List:
            if not(tile.type == 'empty'):
                pygame.draw.rect(screen,[40,40,40],tile)

            Func.text_to_screen(screen,tile.number,tile.x,tile.y)
