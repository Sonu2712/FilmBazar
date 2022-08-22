package com.film.bazar.coredomain

class OrderAction internal constructor(
    @JvmField val id: Int,
    @JvmField val label: String,
    @JvmField val charId: Char,
    private val displayName: String = label
) {

  fun isBuy(): Boolean {
    return this == BUY || this == DEFAULT_BUY
  }

  fun isSell(): Boolean {
    return this == SELL || this == DEFAULT_SELL
  }

  fun reverse(): OrderAction {
    return when (this) {
      BUY -> SELL
      SELL -> BUY
      else -> throw IllegalStateException("Invalid Action")
    }
  }

  fun isDefaultAction(): Boolean {
    return this == DEFAULT_BUY || this == DEFAULT_SELL
  }

  override fun toString(): String {
    return displayName
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as OrderAction

    if (id != other.id) return false
    if (label != other.label) return false
    if (charId != other.charId) return false
    if (displayName != other.displayName) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id
    result = 31 * result + label.hashCode()
    result = 31 * result + charId.hashCode()
    result = 31 * result + displayName.hashCode()
    return result
  }

  companion object {
    @JvmField
    val BUY = OrderAction(0, "Buy", 'B')

    @JvmField
    val SELL = OrderAction(1, "Sell", 'S')

    /**
     * These Instances can be checked for being default.
     * As there id field will be > 9999. Should not be used outside PlaceOrder Screen
     */
    @JvmField
    val DEFAULT_BUY = OrderAction(10000, "Buy", 'B')

    @JvmField
    val DEFAULT_SELL = OrderAction(10001, "Sell", 'S')

    val AllActions = listOf(BUY, SELL)

    @JvmStatic
    fun getInstance(id: Int): OrderAction {
      return AllActions.first { it.id == id }
    }

    @JvmStatic
    fun getInstance(charId: Char): OrderAction {
      return if (charId == BUY.charId) {
        BUY
      } else SELL
    }

    @JvmStatic
    fun getInstance(action: String): OrderAction {
      return AllActions.first { it.label.equals(action, ignoreCase = true) }
    }
  }
}