package com.example.ddraft.ui.ForgeSections

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ddraft.api.responses.ApiListItem
import com.example.ddraft.models.SRD.character_Elements.common.AbilityBonus
import com.example.ddraft.models.SRD.character_Elements.common.Proficiency
import com.example.ddraft.ui.ForgeSections.common.ExpandableSection
import com.example.ddraft.ui.ForgeSections.common.ProficienciesSection
import com.example.ddraft.viewModels.ForgeViewModel
import com.example.ddraft.viewModels.SharedVM

@Composable
fun SpeciesSection(forgeVM:ForgeViewModel, sharedVM: SharedVM){
   Column{
       AnimatedVisibility(
           visible = !forgeVM.speciesVisi.value,
           enter = slideInHorizontally() + fadeIn(),
           exit = slideOutHorizontally() + fadeOut()
       ){
           SpeciesSelection(forgeVM, sharedVM)
       }
       AnimatedVisibility(
           visible = forgeVM.speciesVisi.value,
           enter = slideInHorizontally() + fadeIn(),
           exit = slideOutHorizontally() + fadeOut()
       ){
           SpeciesDetails(forgeVM, sharedVM)
       }
   }
}

@Composable
fun SpeciesSelection(forgeVM: ForgeViewModel, sharedVM: SharedVM){
    Column{
        Text(
            text="Choose a Race",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(15.dp)
        )

        LazyColumn(Modifier.padding(bottom = 100.dp)){
            items(forgeVM.raceList.value.size){
                SpeciesBox(
                    race = forgeVM.raceList.value[it],
                    forgeVM,
                    sharedVM
                )
            }
        }
    }
}

@Composable
private fun SpeciesBox(
    race: ApiListItem,
    forgeVM: ForgeViewModel,
    sharedVM: SharedVM,
){
    Row(modifier = Modifier
        .padding(15.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(sharedVM.midColor.value)
        .padding(horizontal = 15.dp , vertical = 20.dp)
        .fillMaxWidth()
        .clickable { forgeVM.GetRace(race.index)
            Log.d("Race selected", race.name)
            forgeVM.speciesVisiChange()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(race.name)
    }
}

@Composable
fun SpeciesDetails(
    forgeVM: ForgeViewModel,
    sharedVM: SharedVM
){
    val raceChoice = forgeVM.raceChoice.value
    Column{
        Text(
            text=raceChoice?.name?: "Species Name",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(15.dp)
        )

        LazyColumn(Modifier.padding(bottom = 100.dp)){
            item{ GeneralInfo(forgeVM, sharedVM) }

            item{Spacer(Modifier.height(35.dp))}


            item {
                ExpandableSection("Ability Bonuses", sharedVM) {
                    val abilityBonuses = forgeVM.raceChoice.value?.ability_bonuses ?: emptyList()

                    if (abilityBonuses.isNotEmpty()) {
                        BonusInfo(abilityBonuses, sharedVM)
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(sharedVM.midColor.value)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "No ability bonuses",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            item{Spacer(Modifier.height(35.dp))}

            item {
                ExpandableSection("Traits", sharedVM) {
                    val proficiencies = forgeVM.raceChoice.value?.traits ?: emptyList()
                    Log.d("SpeciesDebug", "Race: ${forgeVM.raceChoice.value?.name}, Profs: $proficiencies")
                    if (proficiencies.isNotEmpty()) {
                        ProficienciesSection(proficiencies, sharedVM)
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(sharedVM.brightColor.value)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "No starting proficiencies",
                                color = sharedVM.darkColor.value,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }


            item{ Spacer(Modifier.height(35.dp)) }

            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 7.5.dp),
                    horizontalArrangement = Arrangement.spacedBy(7.5.dp)
                ){
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .weight(1F),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = sharedVM.brightColor.value
                        ),
                        onClick = {forgeVM.speciesVisiChange()}
                    ){
                        Text(
                            text = "Cancel",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = sharedVM.darkColor.value,
                        )
                    }
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .weight(1F),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = sharedVM.brightColor.value
                        ),
                        onClick = {
                            forgeVM.onRaceChosen()
                            forgeVM.onStepChange(forgeVM.selectedStep.value+1)
                        }
                    ){
                        Text(
                            text = "Confirm",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = sharedVM.darkColor.value,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GeneralInfo(forgeVM: ForgeViewModel, sharedVM: SharedVM){
    Row(modifier = Modifier
        .padding(15.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(sharedVM.brightColor.value)
        .padding(horizontal = 15.dp , vertical = 20.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text="Type: ${forgeVM.raceChoice.value?.type}",
            color = sharedVM.darkColor.value
        )

        Text(
            text="Size: ${forgeVM.raceChoice.value?.size}",
            color = sharedVM.darkColor.value
        )

        Text(
            text="Speed: ${forgeVM.raceChoice.value?.speed}ft",
            color = sharedVM.darkColor.value
        )
    }
}

@Composable
private fun BonusInfo(
    abilityBonuses: List<AbilityBonus>,
    sharedVM: SharedVM,
    modifier: Modifier = Modifier
){
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(abilityBonuses.size) { bonus ->
            BonusChip(abilityBonuses[bonus], sharedVM)
        }
    }
}

@Composable
private fun BonusChip(bonus: AbilityBonus, sharedVM: SharedVM){
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = sharedVM.darkColor.value,
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${bonus.ability_score.name.uppercase()} +${bonus.bonus}",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = sharedVM.brightColor.value
            )
        }
    }
}