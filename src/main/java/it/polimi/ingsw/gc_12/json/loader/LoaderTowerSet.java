package it.polimi.ingsw.gc_12.json.loader;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.gc_12.GameMode;
import it.polimi.ingsw.gc_12.occupiables.TowerSet;

import java.lang.reflect.Type;

// TODO: remove it on production
public class LoaderTowerSet extends Loader<TowerSet> {

	public LoaderTowerSet(){
		super("towers");
	}

	@Override
	protected Type getType() {
		return new TypeToken<TowerSet>() {}.getType();
	}

	@Override
	protected TowerSet adapt(TowerSet content, GameMode gameMode) {
		return content;
	}
}