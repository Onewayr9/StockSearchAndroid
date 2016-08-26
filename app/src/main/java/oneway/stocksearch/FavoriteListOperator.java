package oneway.stocksearch;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oneway on 16/4/10.
 */
public class FavoriteListOperator {
    Context context;
    public FavoriteListOperator(Context context){
        this.context = context;
    }
    public boolean containsSymbol(String Symbol){
        SharedPreferences favoriteDetails = context.getSharedPreferences("favorite", Context.MODE_PRIVATE);
        Set<String> favoriteSet = favoriteDetails.getStringSet("favoriteSymbol", new HashSet<String>());
        return favoriteSet.contains(Symbol);
    }
    public void add(String Symbol){
        SharedPreferences favoriteDetails = context.getSharedPreferences("favorite", Context.MODE_PRIVATE);
        SharedPreferences.Editor favoriteEditor = favoriteDetails.edit();
        Set<String> favoriteSet = favoriteDetails.getStringSet("favoriteSymbol", new HashSet<String>());
        favoriteSet.add(Symbol);
        favoriteEditor.clear();
        favoriteEditor.putStringSet("favoriteSymbol",favoriteSet);
        favoriteEditor.commit();
    }
    public void delete(String Symbol){
        SharedPreferences favoriteDetails = context.getSharedPreferences("favorite", Context.MODE_PRIVATE);
        SharedPreferences.Editor favoriteEditor = favoriteDetails.edit();
        Set<String> favoriteSet = favoriteDetails.getStringSet("favoriteSymbol", new HashSet<String>());
        favoriteSet.remove(Symbol);
        favoriteEditor.clear();
        favoriteEditor.putStringSet("favoriteSymbol", favoriteSet);
        favoriteEditor.commit();
    }
    public Set<String> getFavoriteSet(){
        SharedPreferences favoriteDetails = context.getSharedPreferences("favorite", Context.MODE_PRIVATE);
        return favoriteDetails.getStringSet("favoriteSymbol", new HashSet<String>());
    }
    public void clear(){
        SharedPreferences favoriteDetails = context.getSharedPreferences("favorite", Context.MODE_PRIVATE);
        SharedPreferences.Editor favoriteEditor = favoriteDetails.edit();
        favoriteEditor.clear();
        favoriteEditor.commit();
    }
}
