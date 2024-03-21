package extension

fun <T> T.isNull() = this == null

fun <T> T.isNotNull() = this != null