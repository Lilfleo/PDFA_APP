import json

cleaned = []

with open("Food.json", "r", encoding="utf-8") as f:
    for i, line in enumerate(f, start=1):
        line = line.strip()
        if not line:
            continue
        try:
            obj = json.loads(line)
            name = obj.get("name")
            food_group = obj.get("food_group", "Autre")  # Si absent, "Autre"

            if not name:
                continue

            image = f"{name.lower().replace(' ', '_')}.png"

            cleaned.append({
                "id": i,
                "name": name,
                "picture_file_name": image,
                "food_group": food_group
            })
        except json.JSONDecodeError as e:
            print(f"⚠️ Ligne {i} ignorée : {e}")

with open("aliments_clean.json", "w", encoding="utf-8") as f:
    json.dump(cleaned, f, ensure_ascii=False, indent=4)

print(f"✅ aliments_clean.json généré avec {len(cleaned)} aliments.")
# rajouter par IA expiration et calories par unit
