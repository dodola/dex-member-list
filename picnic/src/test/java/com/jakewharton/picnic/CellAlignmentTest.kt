package com.jakewharton.picnic

import com.google.common.truth.Truth.assertThat
import com.jakewharton.picnic.TextAlignment.BottomCenter
import com.jakewharton.picnic.TextAlignment.BottomLeft
import com.jakewharton.picnic.TextAlignment.BottomRight
import com.jakewharton.picnic.TextAlignment.MiddleCenter
import com.jakewharton.picnic.TextAlignment.MiddleLeft
import com.jakewharton.picnic.TextAlignment.MiddleRight
import com.jakewharton.picnic.TextAlignment.TopCenter
import com.jakewharton.picnic.TextAlignment.TopLeft
import com.jakewharton.picnic.TextAlignment.TopRight
import org.junit.Test

class CellAlignmentTest {
  @Test fun alignmentsDoNotAffectSizing() {
    val table = table {
      row {
        cell {
          alignment = TopLeft
          "TL"
        }
        cell {
          alignment = TopCenter
          "TC"
        }
        cell {
          alignment = TopRight
          "TR"
        }
      }
      row {
        cell {
          alignment = MiddleLeft
          "ML"
        }
        cell {
          alignment = MiddleCenter
          "MC"
        }
        cell {
          alignment = MiddleRight
          "MR"
        }
      }
      row {
        cell {
          alignment = BottomLeft
          "BL"
        }
        cell {
          alignment = BottomCenter
          "BC"
        }
        cell {
          alignment = BottomRight
          "BR"
        }
      }
    }

    assertThat(table.renderText()).isEqualTo("""
      |TLTCTR
      |MLMCMR
      |BLBCBR
      |""".trimMargin())
  }

  @Test fun alignmentsAndSizes() {
    val table = table {
      for (alignment in arrayOf<TextAlignment?>(null) + TextAlignment.values()) {
        for (contentWidth in 1..3) {
          if (alignment == null && contentWidth != 1) continue;
          for (contentHeight in 1..2) {
            if (alignment == null && contentHeight != 1) continue;
            row {
              cell(alignment ?: "padding >\n\nalignment\n    v")
              for (paddingLeft in 0..1) {
                for (paddingRight in 0..1) {
                  for (paddingTop in 0..1) {
                    for (paddingBottom in 0..1) {
                      cell {
                        border = true

                        if (alignment == null) {
                          this.alignment = TopLeft
                          """
                            |l$paddingLeft
                            |r$paddingRight
                            |t$paddingTop
                            |b$paddingBottom
                          """.trimMargin()
                        } else {
                          this.paddingLeft = paddingLeft
                          this.paddingRight = paddingRight
                          this.paddingTop = paddingTop
                          this.paddingBottom = paddingBottom
                          this.alignment = alignment

                          ("X".repeat(contentWidth) + '\n').repeat(contentHeight).trimEnd()
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    assertThat(table.renderText()).isEqualTo("""
      |            ┌───┬───┬───┬───┬────┬────┬────┬────┬────┬────┬────┬────┬─────┬─────┬─────┬─────┐
      |padding >   │l0 │l0 │l0 │l0 │l0  │l0  │l0  │l0  │l1  │l1  │l1  │l1  │l1   │l1   │l1   │l1   │
      |            │r0 │r0 │r0 │r0 │r1  │r1  │r1  │r1  │r0  │r0  │r0  │r0  │r1   │r1   │r1   │r1   │
      |alignment   │t0 │t0 │t1 │t1 │t0  │t0  │t1  │t1  │t0  │t0  │t1  │t1  │t0   │t0   │t1   │t1   │
      |    v       │b0 │b1 │b0 │b1 │b0  │b1  │b0  │b1  │b0  │b1  │b0  │b1  │b0   │b1   │b0   │b1   │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopLeft     │X  │X  │   │   │X   │X   │    │    │ X  │ X  │    │    │ X   │ X   │     │     │
      |            │   │   │X  │X  │    │    │X   │X   │    │    │ X  │ X  │     │     │ X   │ X   │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopLeft     │X  │X  │   │   │X   │X   │    │    │ X  │ X  │    │    │ X   │ X   │     │     │
      |            │X  │X  │X  │X  │X   │X   │X   │X   │ X  │ X  │ X  │ X  │ X   │ X   │ X   │ X   │
      |            │   │   │X  │X  │    │    │X   │X   │    │    │ X  │ X  │     │     │ X   │ X   │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopLeft     │XX │XX │   │   │XX  │XX  │    │    │ XX │ XX │    │    │ XX  │ XX  │     │     │
      |            │   │   │XX │XX │    │    │XX  │XX  │    │    │ XX │ XX │     │     │ XX  │ XX  │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopLeft     │XX │XX │   │   │XX  │XX  │    │    │ XX │ XX │    │    │ XX  │ XX  │     │     │
      |            │XX │XX │XX │XX │XX  │XX  │XX  │XX  │ XX │ XX │ XX │ XX │ XX  │ XX  │ XX  │ XX  │
      |            │   │   │XX │XX │    │    │XX  │XX  │    │    │ XX │ XX │     │     │ XX  │ XX  │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopLeft     │XXX│XXX│   │   │XXX │XXX │    │    │ XXX│ XXX│    │    │ XXX │ XXX │     │     │
      |            │   │   │XXX│XXX│    │    │XXX │XXX │    │    │ XXX│ XXX│     │     │ XXX │ XXX │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopLeft     │XXX│XXX│   │   │XXX │XXX │    │    │ XXX│ XXX│    │    │ XXX │ XXX │     │     │
      |            │XXX│XXX│XXX│XXX│XXX │XXX │XXX │XXX │ XXX│ XXX│ XXX│ XXX│ XXX │ XXX │ XXX │ XXX │
      |            │   │   │XXX│XXX│    │    │XXX │XXX │    │    │ XXX│ XXX│     │     │ XXX │ XXX │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopCenter   │ X │ X │   │   │ X  │ X  │    │    │ X  │ X  │    │    │ X   │ X   │     │     │
      |            │   │   │ X │ X │    │    │ X  │ X  │    │    │ X  │ X  │     │     │ X   │ X   │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopCenter   │ X │ X │   │   │ X  │ X  │    │    │ X  │ X  │    │    │ X   │ X   │     │     │
      |            │ X │ X │ X │ X │ X  │ X  │ X  │ X  │ X  │ X  │ X  │ X  │ X   │ X   │ X   │ X   │
      |            │   │   │ X │ X │    │    │ X  │ X  │    │    │ X  │ X  │     │     │ X   │ X   │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopCenter   │XX │XX │   │   │XX  │XX  │    │    │XX  │XX  │    │    │XX   │XX   │     │     │
      |            │   │   │XX │XX │    │    │XX  │XX  │    │    │XX  │XX  │     │     │XX   │XX   │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopCenter   │XX │XX │   │   │XX  │XX  │    │    │XX  │XX  │    │    │XX   │XX   │     │     │
      |            │XX │XX │XX │XX │XX  │XX  │XX  │XX  │XX  │XX  │XX  │XX  │XX   │XX   │XX   │XX   │
      |            │   │   │XX │XX │    │    │XX  │XX  │    │    │XX  │XX  │     │     │XX   │XX   │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopCenter   │XXX│XXX│   │   │XXX │XXX │    │    │XXX │XXX │    │    │XXX  │XXX  │     │     │
      |            │   │   │XXX│XXX│    │    │XXX │XXX │    │    │XXX │XXX │     │     │XXX  │XXX  │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopCenter   │XXX│XXX│   │   │XXX │XXX │    │    │XXX │XXX │    │    │XXX  │XXX  │     │     │
      |            │XXX│XXX│XXX│XXX│XXX │XXX │XXX │XXX │XXX │XXX │XXX │XXX │XXX  │XXX  │XXX  │XXX  │
      |            │   │   │XXX│XXX│    │    │XXX │XXX │    │    │XXX │XXX │     │     │XXX  │XXX  │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopRight    │  X│  X│   │   │   X│   X│    │    │  X │  X │    │    │   X │   X │     │     │
      |            │   │   │  X│  X│    │    │   X│   X│    │    │  X │  X │     │     │   X │   X │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopRight    │  X│  X│   │   │   X│   X│    │    │  X │  X │    │    │   X │   X │     │     │
      |            │  X│  X│  X│  X│   X│   X│   X│   X│  X │  X │  X │  X │   X │   X │   X │   X │
      |            │   │   │  X│  X│    │    │   X│   X│    │    │  X │  X │     │     │   X │   X │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopRight    │ XX│ XX│   │   │  XX│  XX│    │    │ XX │ XX │    │    │  XX │  XX │     │     │
      |            │   │   │ XX│ XX│    │    │  XX│  XX│    │    │ XX │ XX │     │     │  XX │  XX │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopRight    │ XX│ XX│   │   │  XX│  XX│    │    │ XX │ XX │    │    │  XX │  XX │     │     │
      |            │ XX│ XX│ XX│ XX│  XX│  XX│  XX│  XX│ XX │ XX │ XX │ XX │  XX │  XX │  XX │  XX │
      |            │   │   │ XX│ XX│    │    │  XX│  XX│    │    │ XX │ XX │     │     │  XX │  XX │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopRight    │XXX│XXX│   │   │ XXX│ XXX│    │    │XXX │XXX │    │    │ XXX │ XXX │     │     │
      |            │   │   │XXX│XXX│    │    │ XXX│ XXX│    │    │XXX │XXX │     │     │ XXX │ XXX │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |TopRight    │XXX│XXX│   │   │ XXX│ XXX│    │    │XXX │XXX │    │    │ XXX │ XXX │     │     │
      |            │XXX│XXX│XXX│XXX│ XXX│ XXX│ XXX│ XXX│XXX │XXX │XXX │XXX │ XXX │ XXX │ XXX │ XXX │
      |            │   │   │XXX│XXX│    │    │ XXX│ XXX│    │    │XXX │XXX │     │     │ XXX │ XXX │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleLeft  │   │X  │X  │X  │    │X   │X   │X   │    │ X  │ X  │ X  │     │ X   │ X   │ X   │
      |            │X  │   │   │   │X   │    │    │    │ X  │    │    │    │ X   │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleLeft  │   │X  │X  │X  │    │X   │X   │X   │    │ X  │ X  │ X  │     │ X   │ X   │ X   │
      |            │X  │X  │X  │X  │X   │X   │X   │X   │ X  │ X  │ X  │ X  │ X   │ X   │ X   │ X   │
      |            │X  │   │   │   │X   │    │    │    │ X  │    │    │    │ X   │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleLeft  │   │XX │XX │XX │    │XX  │XX  │XX  │    │ XX │ XX │ XX │     │ XX  │ XX  │ XX  │
      |            │XX │   │   │   │XX  │    │    │    │ XX │    │    │    │ XX  │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleLeft  │   │XX │XX │XX │    │XX  │XX  │XX  │    │ XX │ XX │ XX │     │ XX  │ XX  │ XX  │
      |            │XX │XX │XX │XX │XX  │XX  │XX  │XX  │ XX │ XX │ XX │ XX │ XX  │ XX  │ XX  │ XX  │
      |            │XX │   │   │   │XX  │    │    │    │ XX │    │    │    │ XX  │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleLeft  │   │XXX│XXX│XXX│    │XXX │XXX │XXX │    │ XXX│ XXX│ XXX│     │ XXX │ XXX │ XXX │
      |            │XXX│   │   │   │XXX │    │    │    │ XXX│    │    │    │ XXX │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleLeft  │   │XXX│XXX│XXX│    │XXX │XXX │XXX │    │ XXX│ XXX│ XXX│     │ XXX │ XXX │ XXX │
      |            │XXX│XXX│XXX│XXX│XXX │XXX │XXX │XXX │ XXX│ XXX│ XXX│ XXX│ XXX │ XXX │ XXX │ XXX │
      |            │XXX│   │   │   │XXX │    │    │    │ XXX│    │    │    │ XXX │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleCenter│   │ X │ X │ X │    │ X  │ X  │ X  │    │ X  │ X  │ X  │     │ X   │ X   │ X   │
      |            │ X │   │   │   │ X  │    │    │    │ X  │    │    │    │ X   │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleCenter│   │ X │ X │ X │    │ X  │ X  │ X  │    │ X  │ X  │ X  │     │ X   │ X   │ X   │
      |            │ X │ X │ X │ X │ X  │ X  │ X  │ X  │ X  │ X  │ X  │ X  │ X   │ X   │ X   │ X   │
      |            │ X │   │   │   │ X  │    │    │    │ X  │    │    │    │ X   │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleCenter│   │XX │XX │XX │    │XX  │XX  │XX  │    │XX  │XX  │XX  │     │XX   │XX   │XX   │
      |            │XX │   │   │   │XX  │    │    │    │XX  │    │    │    │XX   │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleCenter│   │XX │XX │XX │    │XX  │XX  │XX  │    │XX  │XX  │XX  │     │XX   │XX   │XX   │
      |            │XX │XX │XX │XX │XX  │XX  │XX  │XX  │XX  │XX  │XX  │XX  │XX   │XX   │XX   │XX   │
      |            │XX │   │   │   │XX  │    │    │    │XX  │    │    │    │XX   │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleCenter│   │XXX│XXX│XXX│    │XXX │XXX │XXX │    │XXX │XXX │XXX │     │XXX  │XXX  │XXX  │
      |            │XXX│   │   │   │XXX │    │    │    │XXX │    │    │    │XXX  │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleCenter│   │XXX│XXX│XXX│    │XXX │XXX │XXX │    │XXX │XXX │XXX │     │XXX  │XXX  │XXX  │
      |            │XXX│XXX│XXX│XXX│XXX │XXX │XXX │XXX │XXX │XXX │XXX │XXX │XXX  │XXX  │XXX  │XXX  │
      |            │XXX│   │   │   │XXX │    │    │    │XXX │    │    │    │XXX  │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleRight │   │  X│  X│  X│    │   X│   X│   X│    │  X │  X │  X │     │   X │   X │   X │
      |            │  X│   │   │   │   X│    │    │    │  X │    │    │    │   X │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleRight │   │  X│  X│  X│    │   X│   X│   X│    │  X │  X │  X │     │   X │   X │   X │
      |            │  X│  X│  X│  X│   X│   X│   X│   X│  X │  X │  X │  X │   X │   X │   X │   X │
      |            │  X│   │   │   │   X│    │    │    │  X │    │    │    │   X │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleRight │   │ XX│ XX│ XX│    │  XX│  XX│  XX│    │ XX │ XX │ XX │     │  XX │  XX │  XX │
      |            │ XX│   │   │   │  XX│    │    │    │ XX │    │    │    │  XX │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleRight │   │ XX│ XX│ XX│    │  XX│  XX│  XX│    │ XX │ XX │ XX │     │  XX │  XX │  XX │
      |            │ XX│ XX│ XX│ XX│  XX│  XX│  XX│  XX│ XX │ XX │ XX │ XX │  XX │  XX │  XX │  XX │
      |            │ XX│   │   │   │  XX│    │    │    │ XX │    │    │    │  XX │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleRight │   │XXX│XXX│XXX│    │ XXX│ XXX│ XXX│    │XXX │XXX │XXX │     │ XXX │ XXX │ XXX │
      |            │XXX│   │   │   │ XXX│    │    │    │XXX │    │    │    │ XXX │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |MiddleRight │   │XXX│XXX│XXX│    │ XXX│ XXX│ XXX│    │XXX │XXX │XXX │     │ XXX │ XXX │ XXX │
      |            │XXX│XXX│XXX│XXX│ XXX│ XXX│ XXX│ XXX│XXX │XXX │XXX │XXX │ XXX │ XXX │ XXX │ XXX │
      |            │XXX│   │   │   │ XXX│    │    │    │XXX │    │    │    │ XXX │     │     │     │
      |            │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomLeft  │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │X  │   │X  │    │X   │    │X   │    │ X  │    │ X  │     │ X   │     │ X   │
      |            │X  │   │X  │   │X   │    │X   │    │ X  │    │ X  │    │ X   │     │ X   │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomLeft  │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │X  │   │X  │    │X   │    │X   │    │ X  │    │ X  │     │ X   │     │ X   │
      |            │X  │X  │X  │X  │X   │X   │X   │X   │ X  │ X  │ X  │ X  │ X   │ X   │ X   │ X   │
      |            │X  │   │X  │   │X   │    │X   │    │ X  │    │ X  │    │ X   │     │ X   │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomLeft  │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │XX │   │XX │    │XX  │    │XX  │    │ XX │    │ XX │     │ XX  │     │ XX  │
      |            │XX │   │XX │   │XX  │    │XX  │    │ XX │    │ XX │    │ XX  │     │ XX  │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomLeft  │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │XX │   │XX │    │XX  │    │XX  │    │ XX │    │ XX │     │ XX  │     │ XX  │
      |            │XX │XX │XX │XX │XX  │XX  │XX  │XX  │ XX │ XX │ XX │ XX │ XX  │ XX  │ XX  │ XX  │
      |            │XX │   │XX │   │XX  │    │XX  │    │ XX │    │ XX │    │ XX  │     │ XX  │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomLeft  │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │XXX│   │XXX│    │XXX │    │XXX │    │ XXX│    │ XXX│     │ XXX │     │ XXX │
      |            │XXX│   │XXX│   │XXX │    │XXX │    │ XXX│    │ XXX│    │ XXX │     │ XXX │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomLeft  │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │XXX│   │XXX│    │XXX │    │XXX │    │ XXX│    │ XXX│     │ XXX │     │ XXX │
      |            │XXX│XXX│XXX│XXX│XXX │XXX │XXX │XXX │ XXX│ XXX│ XXX│ XXX│ XXX │ XXX │ XXX │ XXX │
      |            │XXX│   │XXX│   │XXX │    │XXX │    │ XXX│    │ XXX│    │ XXX │     │ XXX │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomCenter│   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │ X │   │ X │    │ X  │    │ X  │    │ X  │    │ X  │     │ X   │     │ X   │
      |            │ X │   │ X │   │ X  │    │ X  │    │ X  │    │ X  │    │ X   │     │ X   │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomCenter│   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │ X │   │ X │    │ X  │    │ X  │    │ X  │    │ X  │     │ X   │     │ X   │
      |            │ X │ X │ X │ X │ X  │ X  │ X  │ X  │ X  │ X  │ X  │ X  │ X   │ X   │ X   │ X   │
      |            │ X │   │ X │   │ X  │    │ X  │    │ X  │    │ X  │    │ X   │     │ X   │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomCenter│   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │XX │   │XX │    │XX  │    │XX  │    │XX  │    │XX  │     │XX   │     │XX   │
      |            │XX │   │XX │   │XX  │    │XX  │    │XX  │    │XX  │    │XX   │     │XX   │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomCenter│   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │XX │   │XX │    │XX  │    │XX  │    │XX  │    │XX  │     │XX   │     │XX   │
      |            │XX │XX │XX │XX │XX  │XX  │XX  │XX  │XX  │XX  │XX  │XX  │XX   │XX   │XX   │XX   │
      |            │XX │   │XX │   │XX  │    │XX  │    │XX  │    │XX  │    │XX   │     │XX   │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomCenter│   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │XXX│   │XXX│    │XXX │    │XXX │    │XXX │    │XXX │     │XXX  │     │XXX  │
      |            │XXX│   │XXX│   │XXX │    │XXX │    │XXX │    │XXX │    │XXX  │     │XXX  │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomCenter│   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │XXX│   │XXX│    │XXX │    │XXX │    │XXX │    │XXX │     │XXX  │     │XXX  │
      |            │XXX│XXX│XXX│XXX│XXX │XXX │XXX │XXX │XXX │XXX │XXX │XXX │XXX  │XXX  │XXX  │XXX  │
      |            │XXX│   │XXX│   │XXX │    │XXX │    │XXX │    │XXX │    │XXX  │     │XXX  │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomRight │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │  X│   │  X│    │   X│    │   X│    │  X │    │  X │     │   X │     │   X │
      |            │  X│   │  X│   │   X│    │   X│    │  X │    │  X │    │   X │     │   X │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomRight │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │  X│   │  X│    │   X│    │   X│    │  X │    │  X │     │   X │     │   X │
      |            │  X│  X│  X│  X│   X│   X│   X│   X│  X │  X │  X │  X │   X │   X │   X │   X │
      |            │  X│   │  X│   │   X│    │   X│    │  X │    │  X │    │   X │     │   X │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomRight │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │ XX│   │ XX│    │  XX│    │  XX│    │ XX │    │ XX │     │  XX │     │  XX │
      |            │ XX│   │ XX│   │  XX│    │  XX│    │ XX │    │ XX │    │  XX │     │  XX │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomRight │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │ XX│   │ XX│    │  XX│    │  XX│    │ XX │    │ XX │     │  XX │     │  XX │
      |            │ XX│ XX│ XX│ XX│  XX│  XX│  XX│  XX│ XX │ XX │ XX │ XX │  XX │  XX │  XX │  XX │
      |            │ XX│   │ XX│   │  XX│    │  XX│    │ XX │    │ XX │    │  XX │     │  XX │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomRight │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │XXX│   │XXX│    │ XXX│    │ XXX│    │XXX │    │XXX │     │ XXX │     │ XXX │
      |            │XXX│   │XXX│   │ XXX│    │ XXX│    │XXX │    │XXX │    │ XXX │     │ XXX │     │
      |            ├───┼───┼───┼───┼────┼────┼────┼────┼────┼────┼────┼────┼─────┼─────┼─────┼─────┤
      |BottomRight │   │   │   │   │    │    │    │    │    │    │    │    │     │     │     │     │
      |            │   │XXX│   │XXX│    │ XXX│    │ XXX│    │XXX │    │XXX │     │ XXX │     │ XXX │
      |            │XXX│XXX│XXX│XXX│ XXX│ XXX│ XXX│ XXX│XXX │XXX │XXX │XXX │ XXX │ XXX │ XXX │ XXX │
      |            │XXX│   │XXX│   │ XXX│    │ XXX│    │XXX │    │XXX │    │ XXX │     │ XXX │     │
      |            └───┴───┴───┴───┴────┴────┴────┴────┴────┴────┴────┴────┴─────┴─────┴─────┴─────┘
      |""".trimMargin())
  }
}
