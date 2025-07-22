import json

# Dictionnaire de traduction français -> anglais
translation_dict = {
    "Pomme": "Apple",
    "Banane": "Banana",
    "Carotte": "Carrot",
    "Tomate": "Tomato",
    "Pomme de terre": "Potato",
    "Oignon": "Onion",
    "Ail": "Garlic",
    "Brocoli": "Broccoli",
    "Épinards": "Spinach",
    "Salade verte": "Green salad",
    "Courgette": "Zucchini",
    "Aubergine": "Eggplant",
    "Poivron rouge": "Red pepper",
    "Concombre": "Cucumber",
    "Radis": "Radish",
    "Champignon de Paris": "Button mushroom",
    "Kiwi": "Kiwi",
    "Orange": "Orange",
    "Poire": "Pear",
    "Fraise": "Strawberry",
    "Pêche": "Peach",
    "Raisin": "Grape",
    "Citron": "Lemon",
    "Ananas": "Pineapple",
    "Melon": "Melon",
    "Pâtes": "Pasta",
    "Riz blanc": "White rice",
    "Pain blanc": "White bread",
    "Farine": "Flour",
    "Avoine": "Oat",
    "Quinoa": "Quinoa",
    "Lentilles": "Lentils",
    "Haricots rouges": "Red beans",
    "Pois chiches": "Chickpeas",
    "Poulet (blanc)": "Chicken (breast)",
    "Bœuf (steak)": "Beef (steak)",
    "Porc (côtelette)": "Pork (chop)",
    "Saumon": "Salmon",
    "Thon": "Tuna",
    "Œufs": "Eggs",
    "Lait entier": "Whole milk",
    "Yaourt nature": "Plain yogurt",
    "Fromage blanc": "Cottage cheese",
    "Emmental": "Emmental cheese",
    "Beurre": "Butter",
    "Huile d'olive": "Olive oil",
    "Sucre blanc": "White sugar",
    "Miel": "Honey",
    "Chocolat noir": "Dark chocolate",
    "Amandes": "Almonds",
    "Noix": "Walnuts",
    "Noisettes": "Hazelnuts",
    "Avocat": "Avocado",
    "Betterave": "Beetroot",
    "Chou-fleur": "Cauliflower",
    "Haricots verts": "Green beans",
    "Petits pois": "Green peas",
    "Maïs": "Corn",
    "Céleri": "Celery",
    "Poireau": "Leek",
    "Endive": "Endive",
    "Ciboulette": "Chives",
    "Persil": "Parsley",
    "Basilic": "Basil",
    "Thym": "Thyme",
    "Origan": "Oregano",
    "Myrtille": "Blueberry",
    "Framboise": "Raspberry",
    "Cerise": "Cherry",
    "Abricot": "Apricot",
    "Prune": "Plum",
    "Mangue": "Mango",
    "Papaye": "Papaya",
    "Pastèque": "Watermelon",
    "Sardine": "Sardine",
    "Crevettes": "Shrimp",
    "Moules": "Mussels",
    "Jambon blanc": "Ham",
    "Bacon": "Bacon",
    "Saucisse": "Sausage",
    "Camembert": "Camembert",
    "Chèvre": "Goat cheese",
    "Roquefort": "Roquefort",
    "Pain complet": "Whole wheat bread",
    "Biscottes": "Rusks",
    "Céréales": "Cereals",
    "Confiture": "Jam",
    "Jus d'orange": "Orange juice",
    "Thé": "Tea",
    "Café": "Coffee",
    "Vinaigre": "Vinegar",
    "Moutarde": "Mustard",
    # "Mayonnaise": "Mayonnaise",
    # "Ketchup": "Ketchup",
    # "Huile de tournesol": "Sunflower oil",
    # "Sel": "Salt",
    # "Poivre": "Pepper",
    # "Paprika": "Paprika",
    # "Cumin": "Cumin",
    # "Cannelle": "Cinnamon"
}

def translate_food_database(input_file, output_file):
    """
    Traduit la base de données d'aliments du français vers l'anglais
    
    Args:
        input_file (str): Chemin vers le fichier JSON français
        output_file (str): Chemin vers le fichier JSON anglais de sortie
    """
    try:
        # Charger le fichier JSON français
        with open(input_file, 'r', encoding='utf-8') as f:
            french_data = json.load(f)
        
        # Créer la version anglaise
        english_data = []
        
        for item in french_data:
            # Copier l'item
            english_item = item.copy()
            
            # Traduire le nom
            french_name = item['name']
            if french_name in translation_dict:
                english_item['name'] = translation_dict[french_name]
            else:
                print(f"Attention: Traduction manquante pour '{french_name}'")
                english_item['name'] = french_name  # Garder le nom français si pas de traduction
            
            # Traduire le lien (nom de fichier)
            french_link = item['link']
            if french_name in translation_dict:
                # Convertir le nom anglais en nom de fichier
                english_filename = translation_dict[french_name].lower().replace(' ', '_').replace('(', '').replace(')', '')
                english_item['link'] = f"{english_filename}.png"
            else:
                english_item['link'] = french_link
            
            english_data.append(english_item)
        
        # Sauvegarder le fichier anglais
        with open(output_file, 'w', encoding='utf-8') as f:
            json.dump(english_data, f, indent=4, ensure_ascii=False)
        
        print(f"✅ Traduction terminée ! Fichier sauvegardé : {output_file}")
        print(f"📊 {len(english_data)} aliments traduits")
        
    except FileNotFoundError:
        print(f"❌ Erreur: Le fichier {input_file} n'a pas été trouvé")
    except json.JSONDecodeError:
        print(f"❌ Erreur: Le fichier {input_file} n'est pas un JSON valide")
    except Exception as e:
        print(f"❌ Erreur inattendue: {e}")

def create_sample_french_database():
    """
    Crée un exemple de base de données française pour tester
    """
    sample_data = [
        {
            "id": 1,
            "name": "Pomme",
            "link": "pomme.png",
            "caloriesPerKg": 520,
            "caloriesPerUnit": 80,
            "expiration_time": "2025-07-21T18:04:13.000Z"
        },
        {
            "id": 2,
            "name": "Banane",
            "link": "banane.png",
            "caloriesPerKg": 890,
            "caloriesPerUnit": 105,
            "expiration_time": "2025-07-14T18:04:13.000Z"
        },
        {
            "id": 3,
            "name": "Poulet (blanc)",
            "link": "poulet_blanc.png",
            "caloriesPerKg": 1650,
            "caloriesPerUnit": 230,
            "expiration_time": "2025-07-10T18:04:13.000Z"
        }
    ]
    
    with open('aliments_clean.json', 'w', encoding='utf-8') as f:
        json.dump(sample_data, f, indent=4, ensure_ascii=False)
    
    print("📝 Exemple de base de données française créé : aliments_clean.json")

def main():
    """
    Fonction principale
    """
    print("🔄 Traducteur de base de données d'aliments FR -> EN")
    print("=" * 50)
    
    # Créer un exemple si nécessaire
    create_example = input("Créer un exemple de base de données française ? (o/n): ")
    if create_example.lower() == 'o':
        create_sample_french_database()
    
    # Demander les fichiers d'entrée et de sortie
    input_file = input("Nom du fichier JSON français (ex: aliments_clean.json): ")
    if not input_file:
        input_file = "aliments_clean.json"
    
    output_file = input("Nom du fichier JSON anglais de sortie (ex: food_database_english.json): ")
    if not output_file:
        output_file = "food_database_english.json"
    
    # Effectuer la traduction
    translate_food_database(input_file, output_file)

if __name__ == "__main__":
    main()