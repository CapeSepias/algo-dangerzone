
/**
 * Which weapon to use in JioJanga game
 */
object JioJangaWeapons {

  def main(args: Array[String]) = {
    println("Chance to hit once")
    println("Prob for revolver = " + probabilityForWeapon(2, 6, 6))
    println("Prob for 2 revolver = " + probabilityForWeapon(2, 6, 12))
    println("Prob for vinchester = " + probabilityForWeapon(3, 6, 6))
    println("Prob for carabin = " + probabilityForWeapon(4, 6, 5))
    println("Prob for hunting rifle = " + probabilityForWeapon(3, 6, 4))
  }

  def probabilityForWeapon(x: Int, y: Int, numberOfShots: Int): Double = {
    var prob = 0.0d
    for (i <- 0 to numberOfShots - 1) {
      prob += (Math.pow((y - x), i) * x) / (Math.pow(y, i + 1))
    }
    prob
  }
}
