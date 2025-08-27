class EcuacionLineal:
    def __init__(self, a, b, c, d, e, f):
        self._a = a
        self._b = b
        self._c = c
        self._d = d
        self._e = e
        self._f = f

    def tieneSolucion(self):
        return (self._a * self._d - self._b * self._c) != 0

    def getX(self):
        return (self._e * self._d - self._b * self._f) / (self._a * self._d - self._b * self._c)

    def getY(self):
        return (self._a * self._f - self._e * self._c) / (self._a * self._d - self._b * self._c)

print("Ingrese a, b, c, d, e, f:")
valores = list(map(float, input().split()))
a, b, c, d, e, f = valores[0], valores[1], valores[2], valores[3], valores[4], valores[5]

ecuacion = EcuacionLineal(a, b, c, d, e, f)

if ecuacion.tieneSolucion():
    print("x =", ecuacion.getX(), ", y =", ecuacion.getY())
else:
    print("La ecuación no tiene solución")