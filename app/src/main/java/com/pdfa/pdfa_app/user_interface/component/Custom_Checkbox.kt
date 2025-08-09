import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pdfa.pdfa_app.ui.theme.AppColors

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    cornerRadius: Dp = 6.dp,
    borderWidth: Dp = 2.dp,
    checkedColor: Color = AppColors.MainGreen,
    uncheckedColor: Color = Color.Transparent,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    checkmarkColor: Color = Color.White,
    animationDuration: Int = 150,
    enabled: Boolean = true
) {
    val checkboxColor by animateColorAsState(
        targetValue = if (checked) checkedColor else uncheckedColor,
        animationSpec = tween(durationMillis = animationDuration),
        label = "checkboxColor"
    )

    val borderColorAnimated by animateColorAsState(
        targetValue = if (checked) checkedColor else borderColor,
        animationSpec = tween(durationMillis = animationDuration),
        label = "borderColor"
    )

    Canvas(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
            .clickable(
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = remember { MutableInteractionSource() },
                indication = null // Pas d'indication pour éviter l'utilisation de rememberRipple
            ) {
                onCheckedChange(!checked)
            }
    ) {
        val cornerRadiusPx = cornerRadius.toPx()
        val borderWidthPx = borderWidth.toPx()

        // Dessiner le fond de la checkbox
        drawRoundRect(
            color = checkboxColor,
            size = this.size,
            cornerRadius = CornerRadius(cornerRadiusPx)
        )

        // Dessiner la bordure
        drawRoundRect(
            color = borderColorAnimated,
            size = this.size,
            cornerRadius = CornerRadius(cornerRadiusPx),
            style = Stroke(width = borderWidthPx)
        )

        // Dessiner le checkmark si coché
        if (checked) {
            drawCheckmark(
                color = checkmarkColor,
                size = this.size
            )
        }
    }
}

private fun DrawScope.drawCheckmark(
    color: Color,
    size: Size
) {
    val path = Path()
    val checkmarkSize = size.minDimension * 0.6f
    val centerX = size.width / 2
    val centerY = size.height / 2
    val left = centerX - checkmarkSize / 2
    val top = centerY - checkmarkSize / 2

    // Créer le chemin du checkmark
    path.moveTo(left + checkmarkSize * 0.2f, top + checkmarkSize * 0.5f)
    path.lineTo(left + checkmarkSize * 0.45f, top + checkmarkSize * 0.75f)
    path.lineTo(left + checkmarkSize * 0.8f, top + checkmarkSize * 0.25f)

    drawPath(
        path = path,
        color = color,
        style = Stroke(
            width = 2.dp.toPx(),
            cap = androidx.compose.ui.graphics.StrokeCap.Round,
            join = androidx.compose.ui.graphics.StrokeJoin.Round
        )
    )
}
