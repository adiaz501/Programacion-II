import math

class Estadistica:
    def __init__(self, n):
        self.__n = n

    def promedio(self):
        suma = 0
        for numero in self.__n:
            suma += numero
        return suma / len(self.__n)

    def desviacion(self):
        n = len(self.__n)
        if n <= 1:
            return 0

        prom = self.promedio()
        suma_cuadrados = 0
        for numero in self.__n:
            suma_cuadrados += (numero - prom) ** 2

        return math.sqrt(suma_cuadrados / (n - 1))



print("Ingrese 10 números separados por espacios:")
n = list(map(float, input().split()))

estadistica = Estadistica(n)

print("El promedio es", estadistica.promedio())
print("La desviación estándar es", estadistica.desviacion())