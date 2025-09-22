import random

class Juego:
    def __init__(self, numeroDeVidas):
        self.numeroDeVidas = numeroDeVidas
        self.record = 0

    def reiniciaPartida(self):
        self.numeroDeVidas = 3
        print("Partida reiniciada. Vidas =", self.numeroDeVidas)

    def actualizaRecord(self):
        self.record += 1
        print("Nuevo record =", self.record)

    def quitaVida(self):
        self.numeroDeVidas -= 1
        if self.numeroDeVidas > 0:
            print("Te quedan", self.numeroDeVidas, "vidas.")
            return True
        else:
            print("No te quedan más vidas.")
            return False

    def __str__(self):
        return f"Juego con {self.numeroDeVidas} vidas, record {self.record}"


class JuegoAdivinaNumero(Juego):
    def __init__(self, numeroDeVidas):
        super().__init__(numeroDeVidas)
        self.numeroAAdivinar = None

    def validaNumero(self, n):
        return 0 <= n <= 10

    def juega(self):
        self.reiniciaPartida()
        self.numeroAAdivinar = random.randint(0, 10)
        print("Adivina un número entre 0 y 10")

        while True:
            try:
                intento = int(input("Ingresa un número: "))
                if not self.validaNumero(intento):
                    print("Número inválido, intenta de nuevo.")
                    continue

                if intento == self.numeroAAdivinar:
                    print("¡¡Acertaste!!")
                    self.actualizaRecord()
                    break
                else:
                    if self.quitaVida():
                        if intento < self.numeroAAdivinar:
                            print("El número a adivinar es mayor.")
                        else:
                            print("El número a adivinar es menor.")
                    else:
                        break
            except ValueError:
                print("Entrada inválida. Por favor, ingresa solo un número.")


class JuegoAdivinaPar(JuegoAdivinaNumero):
    def validaNumero(self, n):
        if not super().validaNumero(n):
            return False
        if n % 2 == 0:
            return True
        else:
            print("Error: el número no es par.")
            return False

class JuegoAdivinaImpar(JuegoAdivinaNumero):
    def validaNumero(self, n):
        if not super().validaNumero(n):
            return False
        if n % 2 != 0:
            return True
        else:
            print("Error: el número no es impar.")
            return False

class Aplicacion:
    def main(self):
        juegos = [JuegoAdivinaNumero(3), JuegoAdivinaPar(3), JuegoAdivinaImpar(3)]
        for juego in juegos:
            print("\nIniciando", type(juego).__name__)
            juego.juega()

if __name__ == "__main__":
    app = Aplicacion()
    app.main()