println("Hello, World!")
val f: PartialFunction[String, String] = {
  case "ping" => "pong"
}

f("ping")
f.isDefinedAt("pong")

trait Generator[+T] {
  self =>

  def generate: T

  def map[S](f: T => S): Generator[S] = new Generator[S] {
    override def generate: S = f(self.generate)
  }

  def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
    override def generate: S = f(self.generate).generate
  }
}

Map(1 -> 2, 2 -> 3, 3 -> 4)