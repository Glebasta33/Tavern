package data.model

/**
 * Модель данных для диалога между персонажами
 *
 * @property isLeft располагается ли текст слева
 * @property text текст для отображения в диаге
 */
data class ReplicaModel(
    val isLeft: Boolean,
    val text: String
)
