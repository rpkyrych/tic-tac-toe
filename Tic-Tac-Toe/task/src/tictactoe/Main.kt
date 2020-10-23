package tictactoe

import java.util.*
import kotlin.system.exitProcess


fun main() {
    val scanner = Scanner(System.`in`)
    var grid = "         "
    printGrid(grid)

    var xCoordinate = 0
    var yCoordinate = 0
    var isTornX = true
    var tornNumber = 0

    loop@ do {
        println("Enter the coordinates: ")
        try {
            xCoordinate = scanner.nextInt()
            yCoordinate = scanner.nextInt()
            if (isCoordinateWrong(xCoordinate) || isCoordinateWrong(yCoordinate)) {
                println("Coordinates should be from 1 to 3!")
                continue@loop
            }
            val charByCoordinates = grid[(xCoordinate - 1) * 3 + yCoordinate - 1]
            if (charByCoordinates == 'X' || charByCoordinates == 'O') {
                println("This cell is occupied! Choose another one!")
                continue@loop
            }
        } catch (e: Exception) {
            println("You should enter numbers!")
        }

        grid = updateGridWithPlayersInput(grid, xCoordinate, yCoordinate, if (isTornX) "X" else "O")
        isTornX = !isTornX
        ++tornNumber
        printGrid(grid)
        checkForWinner(grid)

    } while (tornNumber != 9)
}

private fun checkForWinner(grid: String) {
    // seek for a winner
    var xCount = 0
    var xTotalCount = 0
    var oCount = 0
    var oTotalCount = 0
    var emptyCells = 0
    var isXWin = false
    var isOWin = false
    // horizontal iteration
    for (i in 0..6 step 3) {
        for (j in 0..2) {
            if ('X' == grid[j + i]) {
                ++xCount
                ++xTotalCount
            }
            if ('O' == grid[j + i]) {
                ++oCount
                ++oTotalCount
            }
            if (' ' == grid[j + i]) {
                ++emptyCells
            }
        }
        isXWin = isSomeUserWin(xCount, isXWin)
        isOWin = isSomeUserWin(oCount, isOWin)
        xCount = 0
        oCount = 0
    }

    //vertical iteration if no horizontal win
    if (!isOWin && !isXWin) {
        for (i in 0..2) {
            for (j in 0..6 step 3) {
                if ('X' == grid[j + i]) {
                    ++xCount
                }
                if ('O' == grid[j + i]) {
                    ++oCount
                }
            }
            isXWin = isSomeUserWin(xCount, isXWin)
            isOWin = isSomeUserWin(oCount, isOWin)
            xCount = 0
            oCount = 0
        }
    }

    //cross iteration
    if (!isOWin && !isXWin) {

        for (i in 0..8 step 4) {
            if ('X' == grid[i]) {
                ++xCount
            }
            if ('O' == grid[i]) {
                ++oCount
            }
        }
        isXWin = isSomeUserWin(xCount, isXWin)
        isOWin = isSomeUserWin(oCount, isOWin)
        xCount = 0
        oCount = 0

        for (i in 2..6 step 2) {
            if ('X' == grid[i]) {
                ++xCount
            }
            if ('O' == grid[i]) {
                ++oCount
            }
        }
        isXWin = isSomeUserWin(xCount, isXWin)
        isOWin = isSomeUserWin(oCount, isOWin)
    }

    printGameResult(isOWin, isXWin, emptyCells, oTotalCount, xTotalCount)
}

private fun updateGridWithPlayersInput(grid: String, xCoordinate: Int, yCoordinate: Int, symbol: String): String {
    var updatedGrid = ""
    for (i in grid.indices) {
        if (i == (xCoordinate - 1) * 3 + yCoordinate - 1) {
            updatedGrid += symbol
        } else updatedGrid += grid[i]
    }
    return updatedGrid
}

private fun isSomeUserWin(charCount: Int, isUserWin: Boolean): Boolean {
    var isWin = isUserWin
    if (charCount == 3) {
        isWin = true
    }
    return isWin
}

private fun printGameResult(isOWin: Boolean, isXWin: Boolean, emptyCells: Int, oTotalCount: Int, xTotalCount: Int) {
    if (isOWin && !isXWin && (emptyCells == 0 || emptyCells + oTotalCount + xTotalCount == 9)) {
        println("O wins")
        exitProcess(0)
    } else if (!isOWin && isXWin && (emptyCells == 0 || emptyCells + oTotalCount + xTotalCount == 9)) {
        println("X wins")
        exitProcess(0)
    } else if (!isOWin && !isXWin && emptyCells == 0) {
        println("Draw")
        exitProcess(0)
    }
}

private fun isCoordinateWrong(coordinate: Int) = coordinate < 1 || coordinate > 3

private fun printGrid(grid: String) {
    println("---------")
    println("| ${grid[0]} ${grid[1]} ${grid[2]} |")
    println("| ${grid[3]} ${grid[4]} ${grid[5]} |")
    println("| ${grid[6]} ${grid[7]} ${grid[8]} |")
    println("---------")
}