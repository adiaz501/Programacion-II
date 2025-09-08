from multimethod import multimethod
import math
import numbers

class Circulo2D:
    def __init__(self, x=0, y=0, radio=1):
        self.x = x
        self.y = y
        self.radio = radio

    def get_area(self):
        return math.pi * self.radio * self.radio

    def get_perimetro(self):
        return 2 * math.pi * self.radio

    @multimethod
    def contiene(self, x: numbers.Real, y: numbers.Real):
        """Contiene un punto"""
        distancia = math.sqrt((self.x - x) ** 2 + (self.y - y) ** 2)
        return distancia <= self.radio

    @multimethod
    def contiene(self, c: "Circulo2D"):
        """Contiene otro círculo"""
        distancia = math.sqrt((self.x - c.x) ** 2 + (self.y - c.y) ** 2)
        return distancia + c.radio <= self.radio

    @multimethod
    def sobrepone(self, c: "Circulo2D"):
        """Sobrepone"""
        distancia = math.sqrt((self.x - c.x) ** 2 + (self.y - c.y) ** 2)
        return distancia <= (self.radio + c.radio) and distancia >= abs(self.radio - c.radio)
# -------------------------------
if __name__ == "__main__":
    c1 = Circulo2D(2, 0, 1)

    print("Área:", c1.get_area())
    print("Perímetro:", c1.get_perimetro())

    print("¿Contiene el punto (2.5,0)?", c1.contiene(2.5, 0))
    print("¿Contiene el círculo (2,0,0.5)?", c1.contiene(Circulo2D(2, 0, 0.5)))

    print("¿Se sobrepone con (0,0,2)?", c1.sobrepone(Circulo2D(0, 0, 2)))
