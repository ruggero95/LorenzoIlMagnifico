package it.polimi.ingsw.gc_12.java_fx;

import it.polimi.ingsw.gc_12.*;
import it.polimi.ingsw.gc_12.action.*;
import it.polimi.ingsw.gc_12.card.CardLeaderGuiState;
import it.polimi.ingsw.gc_12.card.CardType;
import it.polimi.ingsw.gc_12.client.ClientHandler;
import it.polimi.ingsw.gc_12.client.ClientFactory;

import it.polimi.ingsw.gc_12.json.loader.LoaderConfig;
import it.polimi.ingsw.gc_12.occupiables.*;
import it.polimi.ingsw.gc_12.resource.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Observable;
import java.util.Observer;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;


public class MainBoardController extends Observable implements Initializable, Observer {
    //Card on floors
    @FXML private ImageView showCards;
    @FXML private ImageView cardFloor0;
    @FXML private ImageView cardFloor1;
    @FXML private ImageView cardFloor2;
    @FXML private ImageView cardFloor3;
    @FXML private ImageView cardFloor4;
    @FXML private ImageView cardFloor5;
    @FXML private ImageView cardFloor6;
    @FXML private ImageView cardFloor7;
    @FXML private ImageView cardFloor8;
    @FXML private ImageView cardFloor9;
    @FXML private ImageView cardFloor10;
    @FXML private ImageView cardFloor11;
    @FXML private ImageView cardFloor12;
    @FXML private ImageView cardFloor13;
    @FXML private ImageView cardFloor14;
    @FXML private ImageView cardFloor15;
    private Map<CardType, List<ImageView>> cardFloors = new HashMap<>();
    //players family member value
    @FXML private Label blackValuePl1;
    @FXML private Label orangeValuePl1;
    @FXML private Label whiteValuePl1;
    @FXML private Label neutralValuePl1;
    @FXML private Label blackValuePl2;
    @FXML private Label orangeValuePl2;
    @FXML private Label whiteValuePl2;
    @FXML private Label neutralValuePl2;
    @FXML private Label blackValuePl3;
    @FXML private Label orangeValuePl3;
    @FXML private Label whiteValuePl3;
    @FXML private Label neutralValuePl3;
    @FXML private Label blackValuePl4;
    @FXML private Label orangeValuePl4;
    @FXML private Label whiteValuePl4;
    @FXML private Label neutralValuePl4;
    //familyMember
    private Map<PlayerColor, Map<FamilyMemberColor, Label>> familyMemberLabels = new HashMap<>();
    //player family member image
    @FXML private ImageView blueBlack;
    @FXML private ImageView blueWhite;
    @FXML private ImageView blueOrange;
    @FXML private ImageView blueNeutral;
    @FXML private ImageView greenBlack;
    @FXML private ImageView greenWhite;
    @FXML private ImageView greenOrange;
    @FXML private ImageView greenNeutral;
    @FXML private ImageView redBlack;
    @FXML private ImageView redWhite;
    @FXML private ImageView redOrange;
    @FXML private ImageView redNeutral;
    @FXML private ImageView yellowBlack;
    @FXML private ImageView yellowWhite;
    @FXML private ImageView yellowOrange;
    @FXML private ImageView yellowNeutral;

    private Map<PlayerColor, Map<FamilyMemberColor, ImageView>> familyMembers = new HashMap<>();

    @FXML private Pane mainPane;

    //pane tb that contains tab of the players
    @FXML private TabPane playersBoards;

    //tab players
    @FXML private Tab bluePlayer;
    @FXML private Tab greenPlayer;
    @FXML private Tab redPlayer;
    @FXML private Tab yellowPlayer;
    private Map<PlayerColor, Tab> playerTabs = new HashMap<>();

    //resource for player
    @FXML private Label moneyValuePl1;
    @FXML private Label stoneValuePl1;
    @FXML private Label servantValuePl1;
    @FXML private Label woodValuePl1;

    @FXML private Label moneyValuePl2;
    @FXML private Label stoneValuePl2;
    @FXML private Label servantValuePl2;
    @FXML private Label woodValuePl2;

    @FXML private Label moneyValuePl3;
    @FXML private Label stoneValuePl3;
    @FXML private Label servantValuePl3;
    @FXML private Label woodValuePl3;

    @FXML private Label moneyValuePl4;
    @FXML private Label stoneValuePl4;
    @FXML private Label servantValuePl4;
    @FXML private Label woodValuePl4;
    private Map<PlayerColor, Map<ResourceType, Label>> resourceLabels = new HashMap<>();

    @FXML private ImageView floorToBeClicked0;
    @FXML private ImageView floorToBeClicked1;
    @FXML private ImageView floorToBeClicked2;
    @FXML private ImageView floorToBeClicked3;
    @FXML private ImageView floorToBeClicked4;
    @FXML private ImageView floorToBeClicked5;
    @FXML private ImageView floorToBeClicked6;
    @FXML private ImageView floorToBeClicked7;
    @FXML private ImageView floorToBeClicked8;
    @FXML private ImageView floorToBeClicked9;
    @FXML private ImageView floorToBeClicked10;
    @FXML private ImageView floorToBeClicked11;
    @FXML private ImageView floorToBeClicked12;
    @FXML private ImageView floorToBeClicked13;
    @FXML private ImageView floorToBeClicked14;
    @FXML private ImageView floorToBeClicked15;
    private Map<CardType, List<ImageView>> towerFloors = new HashMap<>();

    @FXML private Circle firstPlayerTurn;
    @FXML private Circle secondPlayerTurn;
    @FXML private Circle thirdPlayerTurn;
    @FXML private Circle fourthPlayerTurn;
    private List<Circle> turnOrderTrack = new ArrayList<>();

    @FXML private TableView<ResourceRepresentation> tableVictoryPoints;
    @FXML private TableColumn<ResourceRepresentation, String> nameVictoryPoints;
    @FXML private TableColumn<ResourceRepresentation, String> pointsVictoryPoints;

    @FXML private TableView<ResourceRepresentation> tableMilitaryPoints;
    @FXML private TableColumn<ResourceRepresentation, String> nameMilitaryPoints;
    @FXML private TableColumn<ResourceRepresentation, String> pointsMilitaryPoints;

    @FXML private ImageView excomTile1;
    @FXML private ImageView excomTile2;
    @FXML private ImageView excomTile3;
    private List<ImageView> excomTiles = new ArrayList<>();

    @FXML private TextArea chatTextArea;

    @FXML private Button passTurnPl1;

    @FXML private ImageView councilPalace;
    @FXML private ImageView market0;
    @FXML private ImageView market1;
    @FXML private ImageView market2;
    @FXML private ImageView market3;
    private List<ImageView> markets = new ArrayList<>();

    @FXML private ImageView production;
    @FXML private ImageView productionBig;
    @FXML private ImageView harvest;
    @FXML private ImageView harvestBig;

    private Map<WorkType, List<ImageView>> workplaces = new HashMap<>();


    @FXML private ImageView territory0CardPlayer1;
    @FXML private ImageView territory1CardPlayer1;
    @FXML private ImageView territory2CardPlayer1;
    @FXML private ImageView territory3CardPlayer1;
    @FXML private ImageView territory4CardPlayer1;
    @FXML private ImageView territory5CardPlayer1;

    @FXML private ImageView building0CardPlayer1;
    @FXML private ImageView building1CardPlayer1;
    @FXML private ImageView building2CardPlayer1;
    @FXML private ImageView building3CardPlayer1;
    @FXML private ImageView building4CardPlayer1;
    @FXML private ImageView building5CardPlayer1;

    @FXML private ImageView character0CardPlayer1;
    @FXML private ImageView character1CardPlayer1;
    @FXML private ImageView character2CardPlayer1;
    @FXML private ImageView character3CardPlayer1;
    @FXML private ImageView character4CardPlayer1;
    @FXML private ImageView character5CardPlayer1;

    @FXML private ImageView venture0CardPlayer1;
    @FXML private ImageView venture1CardPlayer1;
    @FXML private ImageView venture2CardPlayer1;
    @FXML private ImageView venture3CardPlayer1;
    @FXML private ImageView venture4CardPlayer1;
    @FXML private ImageView venture5CardPlayer1;


    @FXML private ImageView territory0CardPlayer2;
    @FXML private ImageView territory1CardPlayer2;
    @FXML private ImageView territory2CardPlayer2;
    @FXML private ImageView territory3CardPlayer2;
    @FXML private ImageView territory4CardPlayer2;
    @FXML private ImageView territory5CardPlayer2;

    @FXML private ImageView building0CardPlayer2;
    @FXML private ImageView building1CardPlayer2;
    @FXML private ImageView building2CardPlayer2;
    @FXML private ImageView building3CardPlayer2;
    @FXML private ImageView building4CardPlayer2;
    @FXML private ImageView building5CardPlayer2;

    @FXML private ImageView character0CardPlayer2;
    @FXML private ImageView character1CardPlayer2;
    @FXML private ImageView character2CardPlayer2;
    @FXML private ImageView character3CardPlayer2;
    @FXML private ImageView character4CardPlayer2;
    @FXML private ImageView character5CardPlayer2;

    @FXML private ImageView venture0CardPlayer2;
    @FXML private ImageView venture1CardPlayer2;
    @FXML private ImageView venture2CardPlayer2;
    @FXML private ImageView venture3CardPlayer2;
    @FXML private ImageView venture4CardPlayer2;
    @FXML private ImageView venture5CardPlayer2;


    @FXML private ImageView territory0CardPlayer3;
    @FXML private ImageView territory1CardPlayer3;
    @FXML private ImageView territory2CardPlayer3;
    @FXML private ImageView territory3CardPlayer3;
    @FXML private ImageView territory4CardPlayer3;
    @FXML private ImageView territory5CardPlayer3;

    @FXML private ImageView building0CardPlayer3;
    @FXML private ImageView building1CardPlayer3;
    @FXML private ImageView building2CardPlayer3;
    @FXML private ImageView building3CardPlayer3;
    @FXML private ImageView building4CardPlayer3;
    @FXML private ImageView building5CardPlayer3;

    @FXML private ImageView character0CardPlayer3;
    @FXML private ImageView character1CardPlayer3;
    @FXML private ImageView character2CardPlayer3;
    @FXML private ImageView character3CardPlayer3;
    @FXML private ImageView character4CardPlayer3;
    @FXML private ImageView character5CardPlayer3;

    @FXML private ImageView venture0CardPlayer3;
    @FXML private ImageView venture1CardPlayer3;
    @FXML private ImageView venture2CardPlayer3;
    @FXML private ImageView venture3CardPlayer3;
    @FXML private ImageView venture4CardPlayer3;
    @FXML private ImageView venture5CardPlayer3;


    @FXML private ImageView territory0CardPlayer4;
    @FXML private ImageView territory1CardPlayer4;
    @FXML private ImageView territory2CardPlayer4;
    @FXML private ImageView territory3CardPlayer4;
    @FXML private ImageView territory4CardPlayer4;
    @FXML private ImageView territory5CardPlayer4;

    @FXML private ImageView building0CardPlayer4;
    @FXML private ImageView building1CardPlayer4;
    @FXML private ImageView building2CardPlayer4;
    @FXML private ImageView building3CardPlayer4;
    @FXML private ImageView building4CardPlayer4;
    @FXML private ImageView building5CardPlayer4;

    @FXML private ImageView character0CardPlayer4;
    @FXML private ImageView character1CardPlayer4;
    @FXML private ImageView character2CardPlayer4;
    @FXML private ImageView character3CardPlayer4;
    @FXML private ImageView character4CardPlayer4;
    @FXML private ImageView character5CardPlayer4;

    @FXML private ImageView venture0CardPlayer4;
    @FXML private ImageView venture1CardPlayer4;
    @FXML private ImageView venture2CardPlayer4;
    @FXML private ImageView venture3CardPlayer4;
    @FXML private ImageView venture4CardPlayer4;
    @FXML private ImageView venture5CardPlayer4;

    private Map<PlayerColor, Map<CardType, List<ImageView>>> playerCards = new HashMap<>();


    @FXML ImageView cardLeader0Pl1;
    @FXML ImageView cardLeader1Pl1;
    @FXML ImageView cardLeader2Pl1;
    @FXML ImageView cardLeader3Pl1;
    @FXML ImageView cardLeaderPlayed0Pl1;
    @FXML ImageView cardLeaderPlayed1Pl1;
    @FXML ImageView cardLeaderPlayed2Pl1;
    @FXML ImageView cardLeaderPlayed3Pl1;
    private Map<CardLeaderGuiState, List<ImageView>>  mapCardPlayerPl1 = new HashMap<>();

    @FXML ImageView cardLeader0Pl2;
    @FXML ImageView cardLeader1Pl2;
    @FXML ImageView cardLeader2Pl2;
    @FXML ImageView cardLeader3Pl2;
    @FXML ImageView cardLeaderPlayed0Pl2;
    @FXML ImageView cardLeaderPlayed1Pl2;
    @FXML ImageView cardLeaderPlayed2Pl2;
    @FXML ImageView cardLeaderPlayed3Pl2;
    private Map<CardLeaderGuiState, List<ImageView>>  mapCardPlayerPl2 = new HashMap<>();

    @FXML ImageView cardLeader0Pl3;
    @FXML ImageView cardLeader1Pl3;
    @FXML ImageView cardLeader2Pl3;
    @FXML ImageView cardLeader3Pl3;
    @FXML ImageView cardLeaderPlayed0Pl3;
    @FXML ImageView cardLeaderPlayed1Pl3;
    @FXML ImageView cardLeaderPlayed2Pl3;
    @FXML ImageView cardLeaderPlayed3Pl3;
    private Map<CardLeaderGuiState, List<ImageView>>  mapCardPlayerPl3 = new HashMap<>();

    @FXML ImageView cardLeader0Pl4;
    @FXML ImageView cardLeader1Pl4;
    @FXML ImageView cardLeader2Pl4;
    @FXML ImageView cardLeader3Pl4;
    @FXML ImageView cardLeaderPlayed0Pl4;
    @FXML ImageView cardLeaderPlayed1Pl4;
    @FXML ImageView cardLeaderPlayed2Pl4;
    @FXML ImageView cardLeaderPlayed3Pl4;
    private Map<CardLeaderGuiState, List<ImageView>>  mapCardPlayerPl4 = new HashMap<>();

    private Map<PlayerColor, Map<CardLeaderGuiState, List<ImageView>>> cardLeaders = new HashMap<>();


    private ImageView lastFamClicked = null;
    private MatchInstanceGUI match;
    private ClientHandler clientHandler;
    private Action actionPending;
    private List<List<Resource>> councilPrivilegeResources =  new LoaderConfig().get(null).getCouncilPrivilegeResources();
    private PlayerColor playerColor;

    @FXML void familyClicked(MouseEvent event) {
        ImageView familyMemberClicked = (ImageView) event.getTarget();

        if(isMyFam(playerColor, familyMemberClicked) && isMyTurn()){

            for (Map.Entry<FamilyMemberColor, ImageView> entry : familyMembers.get(playerColor).entrySet()) {
                if(entry.getValue().equals(familyMemberClicked)) {
                    highlightFamilyMember(familyMemberClicked);
                    FamilyMember familyMember = new FamilyMember(playerColor, entry.getKey());
                    Action action;
                    if(actionPending != null) {
                        action = new DiscardAction(match.getPlayers().get(playerColor));
                        actionPending = new ActionChooseFamilyMember(match.getPlayers().get(playerColor), familyMember);
                    }
                    else {
                        action = new ActionChooseFamilyMember(match.getPlayers().get(playerColor), familyMember);
                        actionPending = action;
                    }
                    selectAction(action);
                    break;
                }
            }
        }
        else
            showTurnDenied();

        //match.getFamilyMemberBlueRepresentationObservableList().get(0).setValueProperty(10);
    }

    @FXML void showCard(MouseEvent event){
        ImageView imageView = (ImageView) event.getSource();
        Image card = imageView.getImage();
        showCards.setImage(card);
        showCards.setOpacity(1);
    }
    @FXML void showCardLeader(MouseEvent event){
        ImageView imageView = (ImageView) event.getSource();
        Image card = imageView.getImage();
        showCards.setImage(card);
        showCards.setOpacity(1);
        if(event.getClickCount()==2){
            //double click, do actions and turn controls here

        }
    }

    @FXML synchronized void floorClicked(MouseEvent event){
        ImageView floorClicked = (ImageView) event.getTarget();
        if(lastFamClicked!=null && isMyTurn()){
            loop: for(Map.Entry<CardType, List<ImageView>> entryType : towerFloors.entrySet()) {
                for (int i = 0; i < entryType.getValue().size(); i++) {
                    if(entryType.getValue().get(i).equals(floorClicked)) {
                        Action action = new ActionChooseTower(match.getPlayers().get(playerColor), null, new Tower(entryType.getKey()));
                        actionPending = new ActionPlaceOnTower(match.getPlayers().get(playerColor), null, new TowerFloor(i, entryType.getKey()));
                        selectAction(action);
                        break loop;
                    }
                }
            }
        }
        else
            showTurnDenied();
    }

    @FXML void marketClicked(MouseEvent event){
        ImageView market = (ImageView) event.getTarget();
        if(lastFamClicked!=null && isMyTurn()){
            for (int i = 0; i < markets.size(); i++) {
                if(markets.get(i).equals(market)) {
                    Action action = new ActionChooseMarket(match.getPlayers().get(playerColor), null);
                    actionPending = new ActionPlaceOnMarket(match.getPlayers().get(playerColor), null, new SpaceMarket(i, 1, new ArrayList<>()));
                    selectAction(action);
                    break;
                }

            }
        }
        else
            showTurnDenied();
    }

    @FXML void workspaceClicked(MouseEvent event){
        ImageView workplace = (ImageView) event.getTarget();
        if(lastFamClicked!=null && isMyTurn()){
            loop: for(Map.Entry<WorkType, List<ImageView>> entryType : workplaces.entrySet()) {
                for (int i = 0; i < entryType.getValue().size(); i++) {
                    if(entryType.getValue().get(i).equals(workplace)) {
                        Action action = new ActionChooseWorkplace(match.getPlayers().get(playerColor), null);
                        if(i == 0)
                            actionPending = new ActionPlaceOnSpaceWork(match.getPlayers().get(playerColor), null, new SpaceWorkSingle(entryType.getKey()));
                        else
                            actionPending = new ActionPlaceOnSpaceWork(match.getPlayers().get(playerColor), null, new SpaceWorkMultiple(entryType.getKey()));
                        selectAction(action);
                        break loop;
                    }
                }
            }
        }
        else
            showTurnDenied();

    }

    @FXML void passTurn(){
        if(isMyTurn()) {
            Action action = new ActionPassTurn(match.getPlayers().get(playerColor));
            selectAction(action);
        }else
            showTurnDenied();
    }

    private void selectAction(Action action) {
        List<Action> actions = clientHandler.getActions();
        for (int i = 0; i < actions.size(); i++) {
            if(actions.get(i).equals(action)) {
                setChanged();
                notifyObservers(i);
                clientHandler.getEvents().removeFirst();
                clientHandler.handleEvent();
                break;
            }
        }
    }

    public void requestServants() {
        if(isMyTurn()) {
            String request = "Insert the number of servants\nmin value: "+clientHandler.getOffset()+" max value: "+clientHandler.getActions().size();
            int servants = askMeAValue("servant", request, String.valueOf(clientHandler.getOffset()));
            if(actionPending instanceof ActionPlace) {
                ((ActionPlace)actionPending).setServants(new Servant(servants));
                selectAction(actionPending);
                actionPending = null;
            }
        }
    }

    public void setCardFloors(Map<CardType, List<ImageView>> cardFloors) {
        this.cardFloors = cardFloors;
    }

    public void setFamilyMemberLabels(Map<PlayerColor, Map<FamilyMemberColor, Label>> familyMemberLabels) {
        this.familyMemberLabels = familyMemberLabels;
    }

    public void setFamilyMembers(Map<PlayerColor, Map<FamilyMemberColor, ImageView>> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public void setPlayerTabs(Map<PlayerColor, Tab> playerTabs) {
        this.playerTabs = playerTabs;
    }

    public void setResourceLabels(Map<PlayerColor, Map<ResourceType, Label>> resourceLabels) {
        this.resourceLabels = resourceLabels;
    }

    public void setTowerFloors(Map<CardType, List<ImageView>> towerFloors) {
        this.towerFloors = towerFloors;
    }

    public void setTurnOrderTrack(List<Circle> turnOrderTrack) {
        this.turnOrderTrack = turnOrderTrack;
    }

    public void setExcomTiles(List<ImageView> excomTiles) {
        this.excomTiles = excomTiles;
    }

    public void setMarkets(List<ImageView> markets) {
        this.markets = markets;
    }

    public void setWorkplaces(Map<WorkType, List<ImageView>> workplaces) {
        this.workplaces = workplaces;
    }

    public void setPlayerCards(Map<PlayerColor, Map<CardType, List<ImageView>>> playerCards) {
        this.playerCards = playerCards;
    }

    public void setCardLeaders(Map<PlayerColor, Map<CardLeaderGuiState, List<ImageView>>> cardLeaders) {
        this.cardLeaders = cardLeaders;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        match = MatchInstanceGUI.instance();
        match.addObserver(this);
        initializeAllMapsAndLists();


        //disabling all tab player
        bluePlayer.setDisable(true);
        greenPlayer.setDisable(true);
        redPlayer.setDisable(true);
        yellowPlayer.setDisable(true);

        //saying to the table where to find the values in the representation class for Military points
        nameMilitaryPoints.setCellValueFactory(cellData -> cellData.getValue().getPlayerColorProperty());
        pointsMilitaryPoints.setCellValueFactory(cellData -> cellData.getValue().getMilitaryPointProperty().asString());
        //saying to the table where to find the values in the representation class for Victory points
        nameVictoryPoints.setCellValueFactory(cellData -> cellData.getValue().getPlayerColorProperty());
        pointsVictoryPoints.setCellValueFactory(cellData -> cellData.getValue().getVictoryPointProperty().asString());


        showCards.setOpacity(0);
        this.clientHandler = ClientFactory.getClientHandler();
        this.addObserver(ClientFactory.getClientSender());
        clientHandler.setMainBoardController(this);
        if(clientHandler.isStarted())
            setChanged();
        playerColor = clientHandler.getColor();
        notifyObservers(0);
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(() -> {
            bindAllFamilyMember();
            disableTab();
            bindCardsToFloor();
            bindResources();
            bindTrackOrder();
            bindTrackMilitaryVictory();
            bindExcomunocationTile();
            setPlayerToPane();
            bindPlayerCard();
            bindPlayerLeaderCard();
        });
    }

    private void bindAllFamilyMember(){
        Map<PlayerColor, ObservableList<FamilyMemberRepresentation>> mapColorFamilyRepresentation = match.getMapPlayerColorObservableLiseFMRepr();
        for(Player player : match.getPlayers().values()){
            PlayerColor playerColor = player.getColor();
            for(FamilyMemberColor familyMemberColor: FamilyMemberColor.values()){
                //prendo la rappresentazione giusta tramite il famili member e ne prendo la property valore, lo setto poi alla label
                ObservableList<FamilyMemberRepresentation> famMembtakingvalue = mapColorFamilyRepresentation.get(playerColor).stream().filter(FM -> (FM.getColorsFamilyMemberPropertyString()).equals(familyMemberColor.toString())).collect(collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)));
                StringBinding value = famMembtakingvalue.get(0).getValueProperty().asString();//valore da assegnare alla label
                familyMemberLabels.get(playerColor).get(familyMemberColor).textProperty().bind((value));//bindo la property alla label
            }
        }

    }

    private void disableTab(){
        for(Player player : match.getPlayers().values()) {
            Tab tabToWorkWith = playerTabs.get(player.getColor());
            tabToWorkWith.setDisable(false);

            String name= player.getName();
            if(name.length()>25){
                name = player.getName().substring(0,24)+"...";
            }
            tabToWorkWith.setText(name);
        }
    }

    private void bindCardsToFloor(){
        for(CardType cardType : CardType.values()){
            ObservableList<CardFloorRepresentation> cardFloorRepresentations = match.getMapTypeCardFloorRepresentation().get(cardType);
            for(CardFloorRepresentation cardFloorRepresentation : cardFloorRepresentations){
                int floor = cardFloorRepresentation.getFloorNumber();
                cardFloors.get(cardType).get(floor).imageProperty().bind(match.getMapTypeCardFloorRepresentation().get(cardType).get(floor).getPathProperty());
                towerFloors.get(cardType).get(floor).imageProperty().bind(match.getMapTypeCardFloorRepresentation().get(cardType).get(floor).pathWhenTakenProperty());
            }
        }
    }

    private void bindResources(){
        for(Player player : match.getPlayers().values()) {
            ObservableList<ResourceRepresentation> resourceRepresentation = match.getMapPlayerColorResourceRepresentation().get(player.getColor());
            resourceLabels.get(player.getColor()).get(ResourceType.MONEY).textProperty().bind(resourceRepresentation.get(0).getMoneyProperty().asString());
            resourceLabels.get(player.getColor()).get(ResourceType.WOOD).textProperty().bind(resourceRepresentation.get(0).getWoodProperty().asString());
            resourceLabels.get(player.getColor()).get(ResourceType.STONE).textProperty().bind(resourceRepresentation.get(0).getStoneProperty().asString());
            resourceLabels.get(player.getColor()).get(ResourceType.SERVANT).textProperty().bind(resourceRepresentation.get(0).getServantProperty().asString());
        }
    }

    private void bindTrackOrder(){
        List<Player> players = match.getBoard().getTrackTurnOrder().getOrderedPlayers();
        for(int i = 0; i < players.size(); i++){
            turnOrderTrack.get(i).fillProperty().bind(match.getTurnOrderTrackFirstPositionRepresentationObservableList().get(i).getPlayerProperty());
            turnOrderTrack.get(i).setOpacity(1);
        }
    }

    private void bindTrackMilitaryVictory(){
        tableMilitaryPoints.setItems(match.getAllResourcerepresentationMilitary());
        tableVictoryPoints.setItems(match.getAllResourcerepresentationVictory());

    }

    private void bindExcomunocationTile(){
        for(int i = 0; i < excomTiles.size(); i++){
            int j = i+1;
            ExcommunicationTileRepresentation excommunicationTileRepresentation = match.getExcommunicationTileRepresentationObservableList().stream().filter(period -> period.getPeriod() == j).collect(collectingAndThen(toList(), l -> FXCollections.observableArrayList(l))).get(0);
            excomTiles.get(i).imageProperty().bind(excommunicationTileRepresentation.getpathProperty());
        }
    }
    private void bindPlayerCard(){
        for(Player player : match.getPlayers().values()) {
            for(CardType cardType : CardType.values()) {
                for(int i = 0; i < 6; i++) {
                    CardPlayerRepresentation cardPlayerRepresentation = match.getMapPlayerColorCardTypePlayerCard().get(player.getColor()).get(cardType).get(i);
                    playerCards.get(player.getColor()).get(cardType).get(i).imageProperty().bind(cardPlayerRepresentation.getPathProperty());
                }
            }
        }
    }
    private void bindPlayerLeaderCard(){
        Map<PlayerColor, ObservableList<CardLeaderRepresentation>> mapPlayerColorCardLeaders = match.getMapPlayerColorCardLeaders();
        for(Player player : match.getPlayers().values()){
            List<ImageView> listPlayed = cardLeaders.get(player.getColor()).get(CardLeaderGuiState.PLAYED);
            for (int i = 0; i < listPlayed.size(); i++){
               listPlayed.get(i).imageProperty().bind(mapPlayerColorCardLeaders.get(player.getColor()).get(i).getPathWhenPlayedProperty());
                if(playerColor.equals(player.getColor())) {
                    listPlayed.get(i).setVisible(false);
                }


            }
            if(mainPane.getUserData().equals(player.getColor())) {
                List<ImageView> listNotPlayed = cardLeaders.get(player.getColor()).get(CardLeaderGuiState.NOTPLAYED);
                for (int i = 0; i < listNotPlayed.size(); i++) {
                    listNotPlayed.get(i).imageProperty().bind(mapPlayerColorCardLeaders.get(player.getColor()).get(i).getPathProperty());
                }
            }
        }
    }
    private void setPlayerToPane(){
        playersBoards.getSelectionModel().select(playerTabs.get(playerColor));
        mainPane.setUserData(playerColor);
    }

    private Boolean isMyFam(PlayerColor color, ImageView famMemb){
        Map<FamilyMemberColor,ImageView> allMyFamily = familyMembers.get(color);
        for(ImageView imageViewFam: allMyFamily.values()){
            if(imageViewFam.equals(famMemb)){
                return true;
            }
        }
        return false;
    }

    private boolean isMyTurn(){
        return clientHandler.getMyTurn();
    }

    private void showTurnDenied() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Not Your Turn");
        alert.setContentText("Wait your turn bra!");
        alert.showAndWait();
    }

    private void highlightFamilyMember(ImageView familyMemberClicked){

        if(familyMemberClicked.equals(lastFamClicked)){
            double selection = familyMemberClicked.getOpacity();
            if(selection != 1){
                familyMemberClicked.setOpacity(1);
                lastFamClicked = null;
            }
        }else{
            if(lastFamClicked!=null)
                lastFamClicked.setOpacity(1);
            familyMemberClicked.setOpacity(0.5);
            lastFamClicked = familyMemberClicked;
        }

    }

    public TextArea getChat(){
        return chatTextArea;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void sendAction() {
        if(isMyTurn()) {
            if(actionPending != null) {
                selectAction(actionPending);
            }
        }
    }

    public void resetActionPending() {
        actionPending = null;
    }


   public int askMeAValue(String title, String request, String minValue){
       TextInputDialog dialog = new TextInputDialog(minValue);
       dialog.setTitle(title);
       dialog.setHeaderText(request);
       dialog.setContentText("Value:");
       Optional<String> result = dialog.showAndWait();
       int value = 0;
       if (result.isPresent()){
           String resToString = result.get();
           if(isInteger(resToString)) {
               int res = Integer.parseInt(resToString);
               if (res >= clientHandler.getOffset() && res < clientHandler.getActions().size())
                   return res;
           }
           value = askMeAValue(title,request, minValue);
       }
       else {
           Action action = new DiscardAction(match.getPlayers().get(playerColor));
           selectAction(action);
           resetActionPending();
       }
       return value;
   }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    public void handleCouncilPrivilege(CouncilPrivilege councilPrivilege) {

   		if(isMyTurn()) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainBoard.class.getResource("/FXML/DialogCouncilPrivilegeFXML.fxml"));
				Pane page = loader.load();
				Stage dialogStage = new Stage();
				dialogStage.setTitle("Choose Privilege");
				dialogStage.setResizable(false);
				dialogStage.initModality(Modality.WINDOW_MODAL);
				Scene scene = new Scene(page);
				dialogStage.setScene(scene);
				DialogCouncilPrivilegeController controller = loader.getController();
				controller.setDialogStage(dialogStage);

				requestCouncilPrivileges(councilPrivilege, dialogStage, controller);

			} catch (IOException ignored) {}
			finally {
				clientHandler.getEvents().removeFirst();
				clientHandler.handleEvent();
			}
		}
    }

    private void requestCouncilPrivileges(CouncilPrivilege councilPrivilege, Stage dialogStage, DialogCouncilPrivilegeController controller) {
		while(councilPrivilege.getValue() > 0) {
			int choice = askPrivilege(dialogStage, controller);
			ResourceExchange exchange =	new ResourceExchange(new ArrayList<>(Collections.singletonList(new CouncilPrivilege(1))), councilPrivilegeResources.get(choice));
			Action action = new ActionChooseExchange(match.getPlayers().get(playerColor), exchange);
			List<Action> actions = clientHandler.getActions();
			for (int i = 0; i < actions.size(); i++) {
				if(actions.get(i).equals(action)) {
					clientHandler.removeAction(i);
					councilPrivilege.setValue(councilPrivilege.getValue() - 1);
					setChanged();
					notifyObservers(i);
					break;
				}
			}
		}
	}

    private int askPrivilege(Stage dialogStage, DialogCouncilPrivilegeController controller) {
   		int privilege;
		dialogStage.showAndWait();

		if(controller.getSelected()!=-1){
			privilege = controller.getSelected();
			return privilege;
		}else{
			privilege = askPrivilege(dialogStage, controller);
		}
		return privilege;
    }

	public void councilPalaceClicked(MouseEvent event) {
		if(lastFamClicked!=null && isMyTurn()){
			Action action = new ActionPlaceOnCouncil(match.getPlayers().get(playerColor), null, new CouncilPalace(1));
			actionPending = action;
			selectAction(action);
		}
		else
			showTurnDenied();
	}

    public int askWhich(String type, List<ResourceExchange> resources){
        int alternatives = resources.size();
        List<String> choices = new ArrayList<>();
        String str = "";
        int choosen = -1;
        for(ResourceExchange resourceExchange : resources){
            if(resourceExchange.getCost()!=null){
                for (int i = 0; i < resourceExchange.getCost().size(); i++){
                    int value = resourceExchange.getCost().get(i).getValue();
                    String abbreviation = resourceExchange.getCost().get(i).getType().toString().substring(0,1);
                    str = str+" "+value+abbreviation;
                }
            }
            if(resourceExchange.getBonus()!=null){
                str = str+"-->";
                for (int i = 0; i < resourceExchange.getBonus().size(); i++){
                    int value = resourceExchange.getBonus().get(i).getValue();
                    String abbreviation = resourceExchange.getBonus().get(i).getType().toString().substring(0,1);
                    str = str+" "+value+abbreviation;
                }
            }
            choices.add(str);
            str = "";
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Choose " +type);
        dialog.setHeaderText("You can choose between "+alternatives+" "+type);
        dialog.setContentText("Choose :");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            choosen = choices.indexOf(result.get());
        }
        if(choosen==-1){
            choosen = askWhich(type, resources);
        }
        return choosen;
    }

	private void applyPulseEffect(Color color, List<Node> nodes) {
        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(color);
        borderGlow.setSpread(0.5);
        for (Node node: nodes)
            node.setEffect(borderGlow);

        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(borderGlow.colorProperty(), Color.TRANSPARENT);
        final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    private void removeEffects(List<Node> nodes) {
       for(Node node: nodes) {
           node.setEffect(null);
       }
    }

    private void initializeAllMapsAndLists(){
        setFamilyMembers();
        setFamilyMemberLabels();
        setPlayerTabs();
        setCardFloors();
        setResourceLabels();
        setTowerFloors();
        setTrackTurnOrder();
        setExcommunicationTiles();
        setMarkets();
        setWorkplaces();
        setPlayerCards();
        setCardLeaders();
    }

    private void setFamilyMembers() {
        familyMembers.put(PlayerColor.BLUE, new HashMap<FamilyMemberColor, ImageView>() {{
            put(FamilyMemberColor.BLACK, blueBlack);
            put(FamilyMemberColor.WHITE, blueWhite);
            put(FamilyMemberColor.ORANGE, blueOrange);
            put(FamilyMemberColor.NEUTRAL, blueNeutral);
        }});

        familyMembers.put(PlayerColor.GREEN, new HashMap<FamilyMemberColor, ImageView>() {{
            put(FamilyMemberColor.BLACK, greenBlack);
            put(FamilyMemberColor.WHITE, greenWhite);
            put(FamilyMemberColor.ORANGE, greenOrange);
            put(FamilyMemberColor.NEUTRAL, greenNeutral);
        }});

        familyMembers.put(PlayerColor.RED, new HashMap<FamilyMemberColor, ImageView>() {{
            put(FamilyMemberColor.BLACK, redBlack);
            put(FamilyMemberColor.WHITE, redWhite);
            put(FamilyMemberColor.ORANGE, redOrange);
            put(FamilyMemberColor.NEUTRAL, redNeutral);
        }});

        familyMembers.put(PlayerColor.YELLOW, new HashMap<FamilyMemberColor, ImageView>() {{
            put(FamilyMemberColor.BLACK, yellowBlack);
            put(FamilyMemberColor.WHITE, yellowWhite);
            put(FamilyMemberColor.ORANGE, yellowOrange);
            put(FamilyMemberColor.NEUTRAL, yellowNeutral);
        }});
    }

    private void setFamilyMemberLabels() {
        //creating map player list of label player
        familyMemberLabels.put(PlayerColor.BLUE, new HashMap<FamilyMemberColor, Label>() {{
            put(FamilyMemberColor.BLACK, blackValuePl1);
            put(FamilyMemberColor.WHITE, whiteValuePl1);
            put(FamilyMemberColor.ORANGE, orangeValuePl1);
            put(FamilyMemberColor.NEUTRAL, neutralValuePl1);
        }});

        familyMemberLabels.put(PlayerColor.GREEN, new HashMap<FamilyMemberColor, Label>() {{
            put(FamilyMemberColor.BLACK, blackValuePl2);
            put(FamilyMemberColor.WHITE, whiteValuePl2);
            put(FamilyMemberColor.ORANGE, orangeValuePl2);
            put(FamilyMemberColor.NEUTRAL, neutralValuePl2);
        }});

        familyMemberLabels.put(PlayerColor.RED, new HashMap<FamilyMemberColor, Label>() {{
            put(FamilyMemberColor.BLACK, blackValuePl3);
            put(FamilyMemberColor.WHITE, whiteValuePl3);
            put(FamilyMemberColor.ORANGE, orangeValuePl3);
            put(FamilyMemberColor.NEUTRAL, neutralValuePl3);
        }});

        familyMemberLabels.put(PlayerColor.YELLOW, new HashMap<FamilyMemberColor, Label>() {{
            put(FamilyMemberColor.BLACK, blackValuePl4);
            put(FamilyMemberColor.WHITE, whiteValuePl4);
            put(FamilyMemberColor.ORANGE, orangeValuePl4);
            put(FamilyMemberColor.NEUTRAL, neutralValuePl4);
        }});
    }

    private void setPlayerTabs() {
        //associating tab with colo player
        playerTabs.put(PlayerColor.BLUE, bluePlayer);
        playerTabs.put(PlayerColor.GREEN, greenPlayer);
        playerTabs.put(PlayerColor.RED, redPlayer);
        playerTabs.put(PlayerColor.YELLOW, yellowPlayer);
    }

    private void setCardFloors() {
        //associating floor number to id
        cardFloors.put(CardType.TERRITORY, new ArrayList<>(Arrays.asList(cardFloor3, cardFloor2, cardFloor1, cardFloor0)));
        cardFloors.put(CardType.BUILDING, new ArrayList<>(Arrays.asList(cardFloor11, cardFloor10, cardFloor9, cardFloor8)));
        cardFloors.put(CardType.CHARACTER, new ArrayList<>(Arrays.asList(cardFloor7, cardFloor6, cardFloor5, cardFloor4)));
        cardFloors.put(CardType.VENTURE, new ArrayList<>(Arrays.asList(cardFloor15, cardFloor14, cardFloor13, cardFloor12)));
    }

    private void setResourceLabels() {
        //creating the list and map resources
        Map<ResourceType, Label> resourcePlayer1 = new HashMap<>();
        resourcePlayer1.put(ResourceType.MONEY, moneyValuePl1);
        resourcePlayer1.put(ResourceType.STONE, stoneValuePl1);
        resourcePlayer1.put(ResourceType.WOOD, woodValuePl1);
        resourcePlayer1.put(ResourceType.SERVANT, servantValuePl1);

        Map<ResourceType, Label> resourcePlayer2 = new HashMap<>();
        resourcePlayer2.put(ResourceType.MONEY, moneyValuePl2);
        resourcePlayer2.put(ResourceType.STONE, stoneValuePl2);
        resourcePlayer2.put(ResourceType.WOOD, woodValuePl2);
        resourcePlayer2.put(ResourceType.SERVANT, servantValuePl2);

        Map<ResourceType, Label> resourcePlayer3 = new HashMap<>();
        resourcePlayer3.put(ResourceType.MONEY, moneyValuePl3);
        resourcePlayer3.put(ResourceType.STONE, stoneValuePl3);
        resourcePlayer3.put(ResourceType.WOOD, woodValuePl3);
        resourcePlayer3.put(ResourceType.SERVANT, servantValuePl3);

        Map<ResourceType, Label> resourcePlayer4 = new HashMap<>();
        resourcePlayer4.put(ResourceType.MONEY, moneyValuePl4);
        resourcePlayer4.put(ResourceType.STONE, stoneValuePl4);
        resourcePlayer4.put(ResourceType.WOOD, woodValuePl4);
        resourcePlayer4.put(ResourceType.SERVANT, servantValuePl4);

        resourceLabels.put(PlayerColor.BLUE, resourcePlayer1);
        resourceLabels.put(PlayerColor.GREEN, resourcePlayer2);
        resourceLabels.put(PlayerColor.RED, resourcePlayer3);
        resourceLabels.put(PlayerColor.YELLOW, resourcePlayer4);
    }

    private void setTowerFloors() {
        //associating floor number to id
        towerFloors.put(CardType.TERRITORY, new ArrayList<>(Arrays.asList(floorToBeClicked3, floorToBeClicked2, floorToBeClicked1, floorToBeClicked0)));
        towerFloors.put(CardType.BUILDING, new ArrayList<>(Arrays.asList(floorToBeClicked11, floorToBeClicked10, floorToBeClicked9, floorToBeClicked8)));
        towerFloors.put(CardType.CHARACTER, new ArrayList<>(Arrays.asList(floorToBeClicked7, floorToBeClicked6, floorToBeClicked5, floorToBeClicked4)));
        towerFloors.put(CardType.VENTURE, new ArrayList<>(Arrays.asList(floorToBeClicked15, floorToBeClicked14, floorToBeClicked13, floorToBeClicked12)));
    }

    private void setTrackTurnOrder() {
        turnOrderTrack.add(firstPlayerTurn);
        turnOrderTrack.add(secondPlayerTurn);
        turnOrderTrack.add(thirdPlayerTurn);
        turnOrderTrack.add(fourthPlayerTurn);
    }

    private void setExcommunicationTiles() {
        excomTiles.add(excomTile1);
        excomTiles.add(excomTile2);
        excomTiles.add(excomTile3);
    }

    private void setMarkets() {
        markets.add(market0);
        markets.add(market1);
        markets.add(market2);
        markets.add(market3);
    }

    private void setWorkplaces() {
        List<ImageView> productions = new ArrayList<>();
        productions.add(production);
        productions.add(productionBig);
        List<ImageView> harvests = new ArrayList<>();
        harvests.add(harvest);
        harvests.add(harvestBig);
        workplaces.put(WorkType.PRODUCTION, productions);
        workplaces.put(WorkType.HARVEST, harvests);
    }

    private void setPlayerCards() {
        playerCards.put(PlayerColor.BLUE, setFirstPlayerCards());
        playerCards.put(PlayerColor.GREEN, setSecondPlayerCards());
        playerCards.put(PlayerColor.RED, setThirdPlayerCards());
        playerCards.put(PlayerColor.YELLOW, setFourthPlayerCards());
    }

    private Map<CardType, List<ImageView>> setFirstPlayerCards() {
        Map<CardType, List<ImageView>> firstPlayerCards = new HashMap<>();
        //all card of player 1
        List<ImageView> territories = new ArrayList<>();
        territories.add(territory0CardPlayer1);
        territories.add(territory1CardPlayer1);
        territories.add(territory2CardPlayer1);
        territories.add(territory3CardPlayer1);
        territories.add(territory4CardPlayer1);
        territories.add(territory5CardPlayer1);

        List<ImageView> ventures = new ArrayList<>();
        ventures.add(venture0CardPlayer1);
        ventures.add(venture1CardPlayer1);
        ventures.add(venture2CardPlayer1);
        ventures.add(venture3CardPlayer1);
        ventures.add(venture4CardPlayer1);
        ventures.add(venture5CardPlayer1);

        List<ImageView> buildings = new ArrayList<>();
        buildings.add(building0CardPlayer1);
        buildings.add(building1CardPlayer1);
        buildings.add(building2CardPlayer1);
        buildings.add(building3CardPlayer1);
        buildings.add(building4CardPlayer1);
        buildings.add(building5CardPlayer1);

        List<ImageView> characters = new ArrayList<>();
        characters.add(character0CardPlayer1);
        characters.add(character1CardPlayer1);
        characters.add(character2CardPlayer1);
        characters.add(character3CardPlayer1);
        characters.add(character4CardPlayer1);
        characters.add(character5CardPlayer1);

        firstPlayerCards.put(CardType.TERRITORY, territories);
        firstPlayerCards.put(CardType.BUILDING, buildings);
        firstPlayerCards.put(CardType.VENTURE, ventures);
        firstPlayerCards.put(CardType.CHARACTER, characters);

        return firstPlayerCards;
    }

    private Map<CardType, List<ImageView>> setSecondPlayerCards() {
        Map<CardType, List<ImageView>> secondPlayerCards = new HashMap<>();
        //all card of player 1
        List<ImageView> territories = new ArrayList<>();
        territories.add(territory0CardPlayer2);
        territories.add(territory1CardPlayer2);
        territories.add(territory2CardPlayer2);
        territories.add(territory3CardPlayer2);
        territories.add(territory4CardPlayer2);
        territories.add(territory5CardPlayer2);

        List<ImageView> ventures = new ArrayList<>();
        ventures.add(venture0CardPlayer2);
        ventures.add(venture1CardPlayer2);
        ventures.add(venture2CardPlayer2);
        ventures.add(venture3CardPlayer2);
        ventures.add(venture4CardPlayer2);
        ventures.add(venture5CardPlayer2);

        List<ImageView> buildings = new ArrayList<>();
        buildings.add(building0CardPlayer2);
        buildings.add(building1CardPlayer2);
        buildings.add(building2CardPlayer2);
        buildings.add(building3CardPlayer2);
        buildings.add(building4CardPlayer2);
        buildings.add(building5CardPlayer2);

        List<ImageView> characters = new ArrayList<>();
        characters.add(character0CardPlayer2);
        characters.add(character1CardPlayer2);
        characters.add(character2CardPlayer2);
        characters.add(character3CardPlayer2);
        characters.add(character4CardPlayer2);
        characters.add(character5CardPlayer2);

        secondPlayerCards.put(CardType.TERRITORY, territories);
        secondPlayerCards.put(CardType.BUILDING, buildings);
        secondPlayerCards.put(CardType.VENTURE, ventures);
        secondPlayerCards.put(CardType.CHARACTER, characters);

        return secondPlayerCards;
    }

    private Map<CardType, List<ImageView>> setThirdPlayerCards() {
        Map<CardType, List<ImageView>> thirdPlayerCards = new HashMap<>();
        //all card of player 1
        List<ImageView> territories = new ArrayList<>();
        territories.add(territory0CardPlayer3);
        territories.add(territory1CardPlayer3);
        territories.add(territory2CardPlayer3);
        territories.add(territory3CardPlayer3);
        territories.add(territory4CardPlayer3);
        territories.add(territory5CardPlayer3);

        List<ImageView> ventures = new ArrayList<>();
        ventures.add(venture0CardPlayer3);
        ventures.add(venture1CardPlayer3);
        ventures.add(venture2CardPlayer3);
        ventures.add(venture3CardPlayer3);
        ventures.add(venture4CardPlayer3);
        ventures.add(venture5CardPlayer3);

        List<ImageView> buildings = new ArrayList<>();
        buildings.add(building0CardPlayer3);
        buildings.add(building1CardPlayer3);
        buildings.add(building2CardPlayer3);
        buildings.add(building3CardPlayer3);
        buildings.add(building4CardPlayer3);
        buildings.add(building5CardPlayer3);

        List<ImageView> characters = new ArrayList<>();
        characters.add(character0CardPlayer3);
        characters.add(character1CardPlayer3);
        characters.add(character2CardPlayer3);
        characters.add(character3CardPlayer3);
        characters.add(character4CardPlayer3);
        characters.add(character5CardPlayer3);

        thirdPlayerCards.put(CardType.TERRITORY, territories);
        thirdPlayerCards.put(CardType.BUILDING, buildings);
        thirdPlayerCards.put(CardType.VENTURE, ventures);
        thirdPlayerCards.put(CardType.CHARACTER, characters);

        return thirdPlayerCards;
    }

    private Map<CardType, List<ImageView>> setFourthPlayerCards() {
        Map<CardType, List<ImageView>> fourthPlayerCards = new HashMap<>();
        //all card of player 1
        List<ImageView> territories = new ArrayList<>();
        territories.add(territory0CardPlayer4);
        territories.add(territory1CardPlayer4);
        territories.add(territory2CardPlayer4);
        territories.add(territory3CardPlayer4);
        territories.add(territory4CardPlayer4);
        territories.add(territory5CardPlayer4);

        List<ImageView> ventures = new ArrayList<>();
        ventures.add(venture0CardPlayer4);
        ventures.add(venture1CardPlayer4);
        ventures.add(venture2CardPlayer4);
        ventures.add(venture3CardPlayer4);
        ventures.add(venture4CardPlayer4);
        ventures.add(venture5CardPlayer4);

        List<ImageView> buildings = new ArrayList<>();
        buildings.add(building0CardPlayer4);
        buildings.add(building1CardPlayer4);
        buildings.add(building2CardPlayer4);
        buildings.add(building3CardPlayer4);
        buildings.add(building4CardPlayer4);
        buildings.add(building5CardPlayer4);

        List<ImageView> characters = new ArrayList<>();
        characters.add(character0CardPlayer4);
        characters.add(character1CardPlayer4);
        characters.add(character2CardPlayer4);
        characters.add(character3CardPlayer4);
        characters.add(character4CardPlayer4);
        characters.add(character5CardPlayer4);

        fourthPlayerCards.put(CardType.TERRITORY, territories);
        fourthPlayerCards.put(CardType.BUILDING, buildings);
        fourthPlayerCards.put(CardType.VENTURE, ventures);
        fourthPlayerCards.put(CardType.CHARACTER, characters);

        return fourthPlayerCards;
    }

    private void setCardLeaders() {
        Map<CardLeaderGuiState, List<ImageView>> firstPlayerCardLeaders = new HashMap<>();
        firstPlayerCardLeaders.put(CardLeaderGuiState.NOTPLAYED, new ArrayList<>(Arrays.asList(cardLeader0Pl1, cardLeader1Pl1, cardLeader2Pl1, cardLeader3Pl1)));
        firstPlayerCardLeaders.put(CardLeaderGuiState.PLAYED, new ArrayList<>(Arrays.asList(cardLeaderPlayed0Pl1, cardLeaderPlayed1Pl1, cardLeaderPlayed2Pl1, cardLeaderPlayed3Pl1)));

        Map<CardLeaderGuiState, List<ImageView>> secondPlayerCardLeaders = new HashMap<>();
        firstPlayerCardLeaders.put(CardLeaderGuiState.NOTPLAYED, new ArrayList<>(Arrays.asList(cardLeader0Pl2, cardLeader1Pl2, cardLeader2Pl2, cardLeader3Pl2)));
        firstPlayerCardLeaders.put(CardLeaderGuiState.PLAYED, new ArrayList<>(Arrays.asList(cardLeaderPlayed0Pl2, cardLeaderPlayed1Pl2, cardLeaderPlayed2Pl2, cardLeaderPlayed3Pl2)));

        Map<CardLeaderGuiState, List<ImageView>> thirdPlayerCardLeaders = new HashMap<>();
        firstPlayerCardLeaders.put(CardLeaderGuiState.NOTPLAYED, new ArrayList<>(Arrays.asList(cardLeader0Pl3, cardLeader1Pl3, cardLeader2Pl3, cardLeader3Pl3)));
        firstPlayerCardLeaders.put(CardLeaderGuiState.PLAYED, new ArrayList<>(Arrays.asList(cardLeaderPlayed0Pl3, cardLeaderPlayed1Pl3, cardLeaderPlayed2Pl3, cardLeaderPlayed3Pl3)));

        Map<CardLeaderGuiState, List<ImageView>> fourthPlayerCardLeaders = new HashMap<>();
        firstPlayerCardLeaders.put(CardLeaderGuiState.NOTPLAYED, new ArrayList<>(Arrays.asList(cardLeader0Pl4, cardLeader1Pl4, cardLeader2Pl4, cardLeader3Pl4)));
        firstPlayerCardLeaders.put(CardLeaderGuiState.PLAYED, new ArrayList<>(Arrays.asList(cardLeaderPlayed0Pl4, cardLeaderPlayed1Pl4, cardLeaderPlayed2Pl4, cardLeaderPlayed3Pl4)));

        cardLeaders.put(PlayerColor.BLUE, firstPlayerCardLeaders);
        cardLeaders.put(PlayerColor.GREEN, secondPlayerCardLeaders);
        cardLeaders.put(PlayerColor.RED, thirdPlayerCardLeaders);
        cardLeaders.put(PlayerColor.YELLOW, fourthPlayerCardLeaders);
    }

}
