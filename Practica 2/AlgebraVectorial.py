from multimethod import multimethod
import math


class AlgebraVectorial:
    def __init__(self, x=0.0, y=0.0, z=0.0):
        self.x = x
        self.y = y
        self.z = z

    def producto_punto(self, other):
        return self.x * other.x + self.y * other.y + self.z * other.z

    def magnitud(self):
        return math.sqrt(self.x ** 2 + self.y ** 2 + self.z ** 2)

    def producto_cruz(self, other):
        cx = self.y * other.z - self.z * other.y
        cy = self.z * other.x - self.x * other.z
        cz = self.x * other.y - self.y * other.x
        return AlgebraVectorial(cx, cy, cz)

    def sumar(self, other):
        return AlgebraVectorial(self.x + other.x, self.y + other.y, self.z + other.z)

    def restar(self, other):
        return AlgebraVectorial(self.x - other.x, self.y - other.y, self.z - other.z)

    def _es_cero(self, valor):
        return abs(valor) < 1e-9

    # ===== SOBRECARGA DE MÉTODOS PERPENDICULAR =====
    @multimethod
    def perpendicular(self, other):
        """Por defecto usa producto punto (inciso c)"""
        return self.perpendicular(other, 3)

    @multimethod
    def perpendicular(self, other, metodo: int):
        if metodo == 1:  # a) |a+b| = |a-b|
            suma_magnitud = self.sumar(other).magnitud()
            resta_magnitud = self.restar(other).magnitud()
            return self._es_cero(suma_magnitud - resta_magnitud)

        elif metodo == 2:  # b) |a-b| = |b-a|
            ab_magnitud = self.restar(other).magnitud()
            ba_magnitud = other.restar(self).magnitud()
            return self._es_cero(ab_magnitud - ba_magnitud)

        elif metodo == 3:  # c) a · b = 0
            return self._es_cero(self.producto_punto(other))

        elif metodo == 4:  # d) |a+b|² = |a|² + |b|²
            lhs = self.sumar(other).magnitud() ** 2
            rhs = self.magnitud() ** 2 + other.magnitud() ** 2
            return self._es_cero(lhs - rhs)

        else:
            return False

    # ===== SOBRECARGA DE MÉTODOS PARALELA =====
    @multimethod
    def paralela(self, other):
        """Método 1: a = r·b"""
        # Si ambos vectores son cero, se consideran paralelos
        if self._es_cero(self.magnitud()) and self._es_cero(other.magnitud()):
            return True

        # Si solo uno es cero, no son paralelos
        if self._es_cero(self.magnitud()) or self._es_cero(other.magnitud()):
            return False

        # Calcular la razón entre componentes no nulas
        r = None

        if not self._es_cero(other.x):
            r = self.x / other.x
        elif not self._es_cero(other.y):
            r = self.y / other.y
        elif not self._es_cero(other.z):
            r = self.z / other.z

        # Verificar si todas las componentes guardan la misma relación
        if r is not None:
            cond1 = self._es_cero(self.x - r * other.x)
            cond2 = self._es_cero(self.y - r * other.y)
            cond3 = self._es_cero(self.z - r * other.z)
            return cond1 and cond2 and cond3

        return False

    @multimethod
    def paralela(self, other, metodo: int):
        """Método 2: a × b = 0"""
        if metodo == 2:
            cruz = self.producto_cruz(other)
            return (self._es_cero(cruz.x) and
                    self._es_cero(cruz.y) and
                    self._es_cero(cruz.z))
        return self.paralela(other)

    def proyeccion(self, other):
        """Proyección de a sobre b: (a·b/|b|²) * b"""
        if self._es_cero(other.magnitud()):
            return AlgebraVectorial()  # Vector cero si b es cero
        factor = self.producto_punto(other) / (other.magnitud() ** 2)
        return AlgebraVectorial(factor * other.x, factor * other.y, factor * other.z)

    def componente(self, other):
        """Componente de a en la dirección de b: (a·b)/|b|"""
        if self._es_cero(other.magnitud()):
            return 0.0  # Cero si b es cero
        return self.producto_punto(other) / other.magnitud()

    def __str__(self):
        return f"({self.x}, {self.y}, {self.z})"


# ===== PRUEBAS =====
if __name__ == "__main__":
    print("=== PRUEBAS DE ÁLGEBRA VECTORIAL EN PYTHON ===\n")

    # Vectores de prueba
    v1 = AlgebraVectorial(1, 0, 0)  # eje X
    v2 = AlgebraVectorial(0, 1, 0)  # eje Y
    v3 = AlgebraVectorial(2, 0, 0)  # paralelo a v1
    v0 = AlgebraVectorial(0, 0, 0)  # vector cero

    print(">> PERPENDICULARIDAD - SOBRECARGA DEMOSTRADA")
    print("v1.perpendicular(v2):", v1.perpendicular(v2))
    print("v1.perpendicular(v2, 1):", v1.perpendicular(v2, 1))
    print("v1.perpendicular(v2, 2):", v1.perpendicular(v2, 2))
    print("v1.perpendicular(v2, 3):", v1.perpendicular(v2, 3))
    print("v1.perpendicular(v2, 4):", v1.perpendicular(v2, 4))

    print("\n>> PARALELISMO - SOBRECARGA DEMOSTRADA")
    print("v1.paralela(v3):", v1.paralela(v3))
    print("v1.paralela(v3, 2):", v1.paralela(v3, 2))

    print("\n>> PROYECCIÓN Y COMPONENTE")
    a = AlgebraVectorial(3, 4, 0)
    b = AlgebraVectorial(1, 0, 0)
    print(f"Proyección de {a} sobre {b}: {a.proyeccion(b)}")
    print(f"Componente de {a} en dirección de {b}: {a.componente(b)}")

    print("\n>> MANEJO DE VECTOR CERO")
    print(f"v1.paralela(v0): {v1.paralela(v0)}")
    print(f"Proyección de {a} sobre vector cero: {a.proyeccion(v0)}")