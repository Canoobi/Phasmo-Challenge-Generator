package com.files;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PersistanceHandler {
    public Challenge[] getChallengesFromJSON() {
        JSONParser parser = new JSONParser();
        try (InputStream inputStream = SelectChallenge.class.getResourceAsStream("/tasks.json")) {
            Object obj = parser.parse(new InputStreamReader(inputStream));
            JSONArray jsonArray = (JSONArray) obj;
            Challenge[] elements = new Challenge[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject item = (JSONObject) jsonArray.get(i);
                JSONArray array = (JSONArray) item.get("reqPlayers");
                int[] reqPlayers = new int[array.size()];
                for (int j = 0; j < array.size(); j++) {
                    reqPlayers[j] = Integer.parseInt(array.get(j).toString());
                }
                elements[i] = new Challenge((String) item.get("text"), (Boolean) item.get("openSelectItemFrame"), (String) item.get("message"), reqPlayers);
            }
            return elements;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new Challenge[0];
        }
    }

    public Item[] getItemsFromJSON() {
        JSONParser parser = new JSONParser();
        try (InputStream inputStream = SelectChallenge.class.getResourceAsStream("/items.json")) {
            Object obj = parser.parse(new InputStreamReader(inputStream));
            JSONArray jsonArray = (JSONArray) obj;
            Item[] items = new Item[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject item = (JSONObject) jsonArray.get(i);
                items[i] = new Item((String) item.get("text"), (String) item.get("itemType"), (String) item.get("imagePath"), (String) item.get("message"));
            }
            return items;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new Item[0];
        }
    }


    public Map[] getMapsFromJSON() {
        JSONParser parser = new JSONParser();
        try (InputStream inputStream = SelectChallenge.class.getResourceAsStream("/maps.json")) {
            Object obj = parser.parse(new InputStreamReader(inputStream));
            JSONArray jsonArray = (JSONArray) obj;
            Map[] maps = new Map[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject item = (JSONObject) jsonArray.get(i);
                maps[i] = new Map((String) item.get("name"), (String) item.get("size"), (String) item.get("message"));
            }
            return maps;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new Map[0];
        }
    }

    public Penalty[] getPenaltiesFromJSON() {
        JSONParser parser = new JSONParser();
        try (InputStream inputStream = SelectChallenge.class.getResourceAsStream("/penalties.json")) {
            Object obj = parser.parse(new InputStreamReader(inputStream));
            JSONArray jsonArray = (JSONArray) obj;
            Penalty[] penalties = new Penalty[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject item = (JSONObject) jsonArray.get(i);
                penalties[i] = new Penalty((String) item.get("name"), (String) item.get("message"));
            }
            return penalties;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new Penalty[0];
        }
    }

    public String getTextFromSettingsJSON(String item) {
        JSONParser parser = new JSONParser();
        try (InputStream inputStream = SelectChallenge.class.getResourceAsStream("/settings.json")) {
            Object obj = parser.parse(new InputStreamReader(inputStream));
            JSONObject jsonObject = (JSONObject) obj;
            return jsonObject.get(item).toString();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public long getNumFromSettingsJSON(String item) {
        JSONParser parser = new JSONParser();
        try (InputStream inputStream = SelectChallenge.class.getResourceAsStream("/settings.json")) {
            Object obj = parser.parse(new InputStreamReader(inputStream));
            JSONObject jsonObject = (JSONObject) obj;
            return (long) jsonObject.get(item);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String[] getArrayFromSettingsJSON(String item) {
        JSONParser parser = new JSONParser();
        try (InputStream inputStream = SelectChallenge.class.getResourceAsStream("/settings.json")) {
            Object obj = parser.parse(new InputStreamReader(inputStream));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) jsonObject.get(item);
            String[] Array = new String[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                Array[i] = jsonArray.get(i).toString();
            }
            return Array;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new String[0];
        }
    }
}
