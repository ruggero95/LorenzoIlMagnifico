package it.polimi.ingsw.gc12.view.client.gui.representation;

import it.polimi.ingsw.gc12.model.player.PlayerColor;
import it.polimi.ingsw.gc12.model.player.familymember.FamilyMember;
import it.polimi.ingsw.gc12.model.player.familymember.FamilyMemberColor;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

/**
 * It represent the family member that occupy the council Palace
 */
public class CouncilPawnFamily {
    private SimpleObjectProperty<PlayerColor> playerColor;
    private SimpleObjectProperty<FamilyMemberColor> colorFamilyMember;
    private SimpleObjectProperty<Image> familyTemporaryImage;
    private boolean occupied;

    /**
     * Constructor
     * @param playerColor player color owning the family memeber
     * @param familyMemberColor family memeber color present on the council Palace
     * @param path path of the image of the correct family member
     */
    public CouncilPawnFamily(PlayerColor playerColor, FamilyMemberColor familyMemberColor, String path){
        Image temporaryImage = new Image(path);
        this.familyTemporaryImage = new SimpleObjectProperty<Image>(temporaryImage);
        this.playerColor = new SimpleObjectProperty<PlayerColor>(playerColor);
        this.colorFamilyMember = new SimpleObjectProperty<FamilyMemberColor>(familyMemberColor);
    }

    public ObjectProperty<Image> getFamilyTemporaryImage() {
        return familyTemporaryImage;
    }

    /**
     * remove the family member from the councilPalace
     */
    public void removePawn(){
        Image removing = new Image("img/players/transparentPlayer.png");
        familyTemporaryImage.set(removing);
    }

    public boolean isOccupied() {
        return occupied;
    }

    /**
     *
     * @param familyMember
     * @param playerColor
     */
    public void setFamilyMember(FamilyMember familyMember, PlayerColor playerColor) {
        familyTemporaryImage.set(new Image("img/players/"+playerColor.toString()+"/"+playerColor.toString()+"_"+familyMember.getColor().toString()+".png"));
        occupied = true;
    }
}
