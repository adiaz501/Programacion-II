import math

class EcuacionCuadratica:
    def __init__(self, a, b, c):
        self.__a = a
        self.__b = b
        self.__c = c

    def getDiscriminante(self):
        return self.__b * self.__b - 4 * self.__a * self.__c

    def getRaiz1(self):
        discriminante = self.getDiscriminante()
        if discriminante >= 0:
            return (-self.__b + math.sqrt(discriminante)) / (2 * self.__a)
        else:
            return 0

    def getRaiz2(self):
        discriminante = self.getDiscriminante()
        if discriminante >= 0:
            return (-self.__b - math.sqrt(discriminante)) / (2 * self.__a)
        else:
            return 0


print("Ingrese a, b, c:")
valores = list(map(float, input().split()))
a, b, c = valores[0], valores[1], valores[2]

ecuacion = EcuacionCuadratica(a, b, c)
discriminante = ecuacion.getDiscriminante()

if discriminante > 0:
    print("La ecuación tiene dos raíces", ecuacion.getRaiz1(), "y", ecuacion.getRaiz2())
elif discriminante == 0:
    print("La ecuación tiene una raíz", ecuacion.getRaiz1())
else:
    print("La ecuación no tiene raíces reales")