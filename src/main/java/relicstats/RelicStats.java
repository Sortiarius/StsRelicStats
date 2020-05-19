package relicstats;

import basemod.*;
import basemod.abstracts.CustomSavableRaw;
import basemod.interfaces.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import relicstats.patches.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

@SpireInitializer
public class RelicStats implements PostUpdateSubscriber, PostInitializeSubscriber, PostDungeonInitializeSubscriber, EditStringsSubscriber, OnStartBattleSubscriber, CustomSavableRaw {

    private static final Logger logger = LogManager.getLogger(RelicStats.class.getName());
    private static HashMap<String, HasCustomStats> statsInfoHashMap = new HashMap<>();
    public static String statsHeader;
    public static int turnCount;
    public static int battleCount;

    private static String EXTENDED_STATS_OPTION = "extendedStats";
    private static SpireConfig statsConfig;

    public RelicStats(){
        BaseMod.subscribe(this);

        try {
            Properties defaults = new Properties();
            defaults.put(EXTENDED_STATS_OPTION, Boolean.toString(true));
            statsConfig = new SpireConfig("Relic Stats", "config", defaults);
            statsConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void initialize() {
        RelicStats mod = new RelicStats();
    }

    public static void registerCustomStats(String relicId, HasCustomStats customStats) {
        statsInfoHashMap.put(relicId, customStats);
        String saveKey = String.format("stats_%s", relicId);
        BaseMod.addSaveField(saveKey, statsInfoHashMap.get(relicId));
    }

    public void receivePostInitialize() {
        statsHeader = CardCrawlGame.languagePack.getUIString("STATS:HEADER").TEXT[0];
        registerCustomStats(SneckoEye.ID, new SneckoInfo());
        registerCustomStats(CeramicFish.ID, new CeramicFishInfo());
        registerCustomStats(MawBank.ID, new MawBankInfo());
        registerCustomStats(ToyOrnithopter.ID, new ToyOrnithopterInfo());
        registerCustomStats(BurningBlood.ID, new BurningBlackBloodInfo());
        registerCustomStats(BlackBlood.ID, new BurningBlackBloodInfo());
        registerCustomStats(MealTicket.ID, new MealTicketInfo());
        registerCustomStats(SsserpentHead.ID, new SsserpentHeadInfo());
        registerCustomStats(GoldenIdol.ID, new GoldenIdolInfo());
        registerCustomStats(BloodyIdol.ID, new BloodyIdolInfo());
        registerCustomStats(FaceOfCleric.ID, new FaceOfClericInfo());
        registerCustomStats(BlackStar.ID, new BlackStarInfo());
        registerCustomStats(BloodVial.ID, new BloodVialInfo());
        registerCustomStats(JuzuBracelet.ID, new JuzuBraceletInfo());
        registerCustomStats(Ectoplasm.ID, new EctoplasmInfo());
        registerCustomStats(Sozu.ID, new SozuInfo());
        registerCustomStats(Boot.ID, BootInfo.getInstance());
        registerCustomStats(PreservedInsect.ID, new PreservedInsectInfo());
        registerCustomStats(MeatOnTheBone.ID, new MeatOnTheBoneInfo());
        registerCustomStats(Pantograph.ID, new PantographInfo());
        registerCustomStats(Torii.ID, new ToriiInfo());
        registerCustomStats(TungstenRod.ID, new TungstenRodInfo());
        registerCustomStats(MercuryHourglass.ID, MercuryHourglassInfo.getInstance());
        registerCustomStats(StoneCalendar.ID, StoneCalendarInfo.getInstance());
        registerCustomStats(DarkstonePeriapt.ID, new DarkstonePeriaptInfo());
        registerCustomStats(SingingBowl.ID, new SingingBowlInfo());
        registerCustomStats(Shuriken.ID, ShurikenInfo.getInstance());
        registerCustomStats(Kunai.ID, KunaiInfo.getInstance());
        registerCustomStats(OrnamentalFan.ID, OrnamentalFanInfo.getInstance());
        registerCustomStats(MarkOfTheBloom.ID, new MarkOfTheBloomInfo());
        registerCustomStats(ArtOfWar.ID, ArtOfWarInfo.getInstance());
        registerCustomStats(Nunchaku.ID, NunchakuInfo.getInstance());
        registerCustomStats(SneckoSkull.ID, SneckoSkullInfo.getInstance());
        registerCustomStats(LetterOpener.ID, LetterOpenerInfo.getInstance());
        registerCustomStats(Sundial.ID, SundialInfo.getInstance());
        registerCustomStats(CharonsAshes.ID, CharonsAshesInfo.getInstance());
        registerCustomStats(CloakClasp.ID, CloakClaspInfo.getInstance());
        registerCustomStats(Pocketwatch.ID, PocketwatchInfo.getInstance());
        registerCustomStats(Tingsha.ID, TingshaInfo.getInstance());
        registerCustomStats(ToughBandages.ID, ToughBandagesInfo.getInstance());
        registerCustomStats(VioletLotus.ID, VioletLotusInfo.getInstance());
        registerCustomStats(Abacus.ID, AbacusInfo.getInstance());
        registerCustomStats(DeadBranch.ID, DeadBranchInfo.getInstance());
        registerCustomStats(UnceasingTop.ID, UnceasingTopInfo.getInstance());
        registerCustomStats(FrozenCore.ID, FrozenCoreInfo.getInstance());
        registerCustomStats(HoveringKite.ID, HoveringKiteInfo.getInstance());
        registerCustomStats(RunicCube.ID, RunicCubeInfo.getInstance());
        registerCustomStats(StrangeSpoon.ID, StrangeSpoonInfo.getInstance());
        registerCustomStats(NlothsGift.ID, new NlothsGiftInfo());
        registerCustomStats(SelfFormingClay.ID, SelfFormingClayInfo.getInstance());
        registerCustomStats(Necronomicon.ID, NecronomiconInfo.getInstance());
        registerCustomStats(MedicalKit.ID, MedicalKitInfo.getInstance());
        registerCustomStats(BlueCandle.ID, BlueCandleInfo.getInstance());
        registerCustomStats(Orichalcum.ID, OrichalcumInfo.getInstance());
        registerCustomStats(PenNib.ID, PenNibInfo.getInstance());
        registerCustomStats(SmilingMask.ID, new SmilingMaskInfo());
        registerCustomStats(GremlinHorn.ID, GremlinHornInfo.getInstance());
        registerCustomStats(InkBottle.ID, InkBottleInfo.getInstance());
        registerCustomStats(BirdFacedUrn.ID, BirdFacedUrnInfo.getInstance());
        registerCustomStats(ChampionsBelt.ID, ChampionsBeltInfo.getInstance());
        registerCustomStats(Ginger.ID, GingerInfo.getInstance());
        registerCustomStats(Turnip.ID, TurnipInfo.getInstance());
        registerCustomStats(PrayerWheel.ID, new PrayerWheelInfo());
        registerCustomStats(Inserter.ID, InserterInfo.getInstance());
        registerCustomStats(HandDrill.ID, HandDrillInfo.getInstance());
        registerCustomStats(OrangePellets.ID, OrangePelletsInfo.getInstance());

        System.out.println("Custom stat relics: ");
        System.out.println(Arrays.toString(statsInfoHashMap.keySet().toArray()));

        BaseMod.addSaveField("stats_master_turn_counts", this);

        setUpOptions();
    }

    public static boolean getExtendedStatsOption() {
        if (statsConfig == null) {
            return false;
        }
        return statsConfig.getBool(EXTENDED_STATS_OPTION);
    }

    private void setUpOptions() {
        ModPanel settingsPanel = new ModPanel();
        ModLabeledToggleButton extendedStatsButton = new ModLabeledToggleButton(
                CardCrawlGame.languagePack.getUIString("STATS:OPTION").TEXT[0],
                350, 700, Settings.CREAM_COLOR, FontHelper.charDescFont,
                getExtendedStatsOption(), settingsPanel, modLabel -> {},
                modToggleButton -> {
                    if (statsConfig != null) {
                        statsConfig.setBool(EXTENDED_STATS_OPTION, modToggleButton.enabled);
                        try {
                            statsConfig.save();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        settingsPanel.addUIElement(extendedStatsButton);
        BaseMod.registerModBadge(ImageMaster.loadImage("Icon.png"),"Relic Stats", "Forgotten Arbiter", null, settingsPanel);
    }

    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(UIStrings.class, "localization/eng/descriptions.json");
    }

    public void receivePostDungeonInitialize() {
        for(HasCustomStats statsInfo : statsInfoHashMap.values()) {
            statsInfo.resetStats();
        }
    }

    public static boolean hasStats(String relicId) {
        return statsInfoHashMap.containsKey(relicId);
    }

    public static String getStatsDescription(String relicId) {
        if (getExtendedStatsOption()) {
            return statsInfoHashMap.get(relicId).getExtendedStatsDescription();
        } else {
            return statsInfoHashMap.get(relicId).getStatsDescription();
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (statsInfoHashMap.containsKey(relic.relicId)) {
                statsInfoHashMap.get(relic.relicId).onCombatStartForStats();
            }
        }
        battleCount += 1;
    }

    @Override
    public JsonElement onSaveRaw() {
        Gson gson = new Gson();
        ArrayList<Integer> stats = new ArrayList<>();
        stats.add(battleCount);
        stats.add(turnCount);
        return gson.toJsonTree(stats);
    }

    @Override
    public void onLoadRaw(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            battleCount = jsonArray.get(0).getAsInt();
            turnCount = jsonArray.get(1).getAsInt();
        } else {
            battleCount = 0;
            turnCount = 0;
        }

    }

    public void receivePostUpdate() {
        SlayTheRelicsIntegration.clear();
        if (CardCrawlGame.isInARun()) {
            for (AbstractRelic relic : AbstractDungeon.player.relics) {
                if (statsInfoHashMap.containsKey(relic.relicId)) {
                    Hitbox hb = relic.hb;
                    PowerTip tip = new PowerTip(statsHeader, getStatsDescription(relic.relicId));
                    ArrayList<PowerTip> tips = (ArrayList<PowerTip>)relic.tips.clone();
                    tips.add(tip);
                    SlayTheRelicsIntegration.renderTipHitbox(hb, tips);
                }
            }
        }
    }
}
