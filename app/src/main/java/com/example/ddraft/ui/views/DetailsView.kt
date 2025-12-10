package com.example.ddraft.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ddraft.R
import com.example.ddraft.models.EquipmentItem
import com.example.ddraft.models.Character
import com.example.ddraft.ui.theme.ClassTheme
import com.example.ddraft.ui.theme.LightBlack
import com.example.ddraft.viewModels.SharedVM

@Composable
fun DetailsScreen(
    sharedVM: SharedVM,
    nc: NavController,
    modifier: Modifier = Modifier
) {
    val character by sharedVM.current
    val classTheme = ClassTheme.getTheme(character?.className)
    val classIcon = classTheme?.iconRes ?: R.drawable.search

    Box(
        modifier = modifier
            .background(LightBlack)
            .fillMaxSize()
    ) {
        Column {
            Spacer(Modifier.height(35.dp))
            HeaderSection(character, classIcon, sharedVM)
            Spacer(Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { BasicInfoSection(character) }
                item { AbilityScoresSection(character) }
                item { CurrencySection(character) }
                item { TotalCurrencySection(character) }
                item { EquipmentSummarySection(character, sharedVM) }
                item { ClassProficienciesSection(character, sharedVM) }
                item { RacialTraitsSection(character, sharedVM) }
                item { DeleteCharacterSection(character, sharedVM, nc) }
            }
        }
    }
}

@Composable
private fun CardSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
    bgColor: Color,
    textColor: Color
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun HeaderSection(
    character: Character?,
    classIcon: Int,
    sharedVM: SharedVM
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(sharedVM.brightColor.value)
            .padding(24.dp)
    ) {
        Image(painterResource(classIcon), contentDescription = "${character?.className} Icon", Modifier.size(80.dp))
        Spacer(Modifier.height(12.dp))
        Text(character?.name ?: "No Character", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = sharedVM.darkColor.value)
        Text("${character?.className ?: ""} - Level ${character?.level ?: 0}", fontSize = 18.sp, color = sharedVM.darkColor.value, fontWeight = FontWeight.Medium)
        Text("${character?.raceName ?: ""} ${character?.backgroundName ?: ""} (${character?.alignment ?: ""})", fontSize = 16.sp, color = sharedVM.darkColor.value.copy(alpha = 0.8f))
    }
}

@Composable
private fun BasicInfoSection(character: Character?) {
    CardSection(
        "Basic Information",
        {
            InfoRow("Class", character?.className ?: "", character?.darkColor ?: Color.Black)
            InfoRow("Race", character?.raceName ?: "", character?.darkColor ?: Color.Black)
            InfoRow("Background", character?.backgroundName ?: "", character?.darkColor ?: Color.Black)
            InfoRow("Alignment", character?.alignment ?: "", character?.darkColor ?: Color.Black)
            InfoRow("Level", character?.level?.toString() ?: "", character?.darkColor ?: Color.Black)
        },
        character?.lightColor ?: Color.Gray,
        character?.darkColor ?: Color.Black
    )
}

@Composable
private fun AbilityScoresSection(character: Character?) {
    CardSection(
        "Ability Scores",
        {
            AbilityScoreRow("STR", character?.strength ?: 8, character?.darkColor ?: Color.Black)
            AbilityScoreRow("DEX", character?.dexterity ?: 8, character?.darkColor ?: Color.Black)
            AbilityScoreRow("CON", character?.constitution ?: 8, character?.darkColor ?: Color.Black)
            AbilityScoreRow("INT", character?.intelligence ?: 8, character?.darkColor ?: Color.Black)
            AbilityScoreRow("WIS", character?.wisdom ?: 8, character?.darkColor ?: Color.Black)
            AbilityScoreRow("CHA", character?.charisma ?: 8, character?.darkColor ?: Color.Black)
        },
        character?.mediumColor ?: Color.Gray,
        character?.darkColor ?: Color.Black
    )
}

@Composable
private fun CurrencySection(character: Character?) {
    CardSection(
        "Currency Breakdown",
        {
            InfoRow("Gold Pieces", "${character?.goldPieces ?: "0"} gp", character?.darkColor ?: Color.Black)
            InfoRow("Silver Pieces", "${character?.silverPieces ?: "0"} sp", character?.darkColor ?: Color.Black)
            InfoRow("Copper Pieces", "${character?.copperPieces ?: "0"} cp", character?.darkColor ?: Color.Black)
        },
        character?.lightColor ?: Color.Gray,
        character?.darkColor ?: Color.Black
    )
}

@Composable
private fun TotalCurrencySection(character: Character?) {
    val gp = character?.goldPieces?.toIntOrNull() ?: 0
    val sp = character?.silverPieces?.toIntOrNull() ?: 0
    val cp = character?.copperPieces?.toIntOrNull() ?: 0
    val totalGp = gp + sp / 10f + cp / 100f

    CardSection(
        "Total Wealth",
        {
            Text("${totalGp}gp total", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = character?.lightColor ?: Color.Gray)
            Text("(${gp}gp + ${sp}sp + ${cp}cp)", fontSize = 14.sp, color = (character?.lightColor ?: Color.Gray).copy(alpha = 0.7f))
        },
        character?.darkColor ?: Color.Black,
        character?.lightColor ?: Color.Gray
    )
}

@Composable
private fun EquipmentSummarySection(character: Character?, sharedVM: SharedVM) {
    CardSection(
        "Equipment (${character?.selectedEquipment?.size ?: 0} items)",
        {
            if (character?.selectedEquipment?.isEmpty() == true) {
                Text("No equipment selected", color = (character.darkColor ?: Color.Black).copy(alpha = 0.7f), fontSize = 16.sp)
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(200.dp)
                ) {
                    items(character?.selectedEquipment ?: emptyList()) { equipment ->
                        EquipmentItemRow(equipment, sharedVM)
                    }
                }
            }
        },
        character?.mediumColor ?: Color.Gray,
        character?.darkColor ?: Color.Black
    )
}

@Composable
private fun ClassProficienciesSection(character: Character?, sharedVM: SharedVM) {
    val classProfs = listOf("Athletics", "Intimidation", "Survival", "Perception")

    CardSection(
        "${character?.className ?: "Class"} Proficiencies",
        {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.height(60.dp)) {
                items(classProfs) { prof -> ProficiencyChip(prof, sharedVM) }
            }
        },
        character?.lightColor ?: Color.Gray,
        character?.darkColor ?: Color.Black
    )
}

@Composable
private fun RacialTraitsSection(character: Character?, sharedVM: SharedVM) {
    val raceTraits = listOf("Darkvision", "Keen Senses", "Fey Ancestry", "Trance")

    CardSection(
        "${character?.raceName ?: "Race"} Traits",
        {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.height(60.dp)) {
                items(raceTraits) { trait -> TraitChip(trait, sharedVM) }
            }
        },
        character?.mediumColor ?: Color.Gray,
        character?.lightColor ?: Color.Gray
    )
}

@Composable
private fun InfoRow(label: String, value: String, textColor: Color) {
    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = textColor)
        Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = textColor)
    }
}

@Composable
private fun AbilityScoreRow(label: String, score: Int, textColor: Color) {
    val modifier = (score - 10) / 2
    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = textColor)
        Text(
            text = "$score (${if (modifier >= 0) "+" else ""}$modifier)",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (modifier >= 0) Color.Green else Color.Red
        )
    }
}

@Composable
private fun EquipmentItemRow(equipment: EquipmentItem, sharedVM: SharedVM) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            equipment.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Text(
            "x${equipment.quantity}",
            fontSize = 16.sp,
            color = sharedVM.brightColor.value,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ProficiencyChip(proficiency: String, sharedVM: SharedVM) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = sharedVM.darkColor.value.copy(alpha = 0.75f),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(
            proficiency,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = sharedVM.brightColor.value,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun TraitChip(trait: String, sharedVM: SharedVM) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = sharedVM.brightColor.value.copy(alpha = 0.75f),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(
            trait,
            fontSize = 14.sp,
            color = sharedVM.darkColor.value,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun DeleteCharacterSection(
    character: Character?,
    sharedVM: SharedVM,
    nc: NavController
) {
    if (character == null) return
    CardSection(
        "Danger Zone",
        {
            Button(
                onClick = {
                    sharedVM.deleteCharacter(character)
                    sharedVM.onCurrentChange(sharedVM.characters.value.firstOrNull() ?: return@Button)
                    nc.navigate("home")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.8f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_delete),
                        contentDescription = "Delete",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Delete ${character.name}",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        Color.Red.copy(alpha = 0.1f),
        Color.Red
    )
}