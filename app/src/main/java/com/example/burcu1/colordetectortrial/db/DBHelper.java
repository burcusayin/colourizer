package com.example.burcu1.colordetectortrial.db;

/**
 * Created by Burcu1 on 26.2.2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.HashMap;
import static java.lang.Math.pow;

public class DBHelper extends SQLiteOpenHelper{

    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "detector.db";

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_COLORS = "CREATE TABLE " + ColorProperties.TABLE  + "("
                + ColorProperties.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + ColorProperties.KEY_name + " TEXT, "
                + ColorProperties.KEY_code + " STRING )";

        db.execSQL(CREATE_TABLE_COLORS);
        String path = db.getPath();
        Log.w("DB!!!1",path);

        populateDB(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + ColorProperties.TABLE);

        // Create tables again
        this.onCreate(db);
        populateDB(db);

    }

    //---------------------------------------------------------------------------------------------

    public void populateDB(SQLiteDatabase db)
    {
        int i;

        String [] colorNames1 = {"Acid Green", "Aero", "Aero Blue", "African Violet", "Air Force Blue", "Air Force Blue", "Air Superiority Blue", "Alabama Crimson", "Alice Blue", "Alizarin Crimson", "Alloy Orange", "Almond", "Amaranth", "Amaranth Deep Purple", "Amaranth Pink", "Amaranth Purple", "Amaranth Red", "Amazon", "Amber", "Amber", "American Rose", "Amethyst", "Android Green", "Anti-Flash White", "Antique Brass", "Antique Bronze", "Antique Fuchsia", "Antique Ruby", "Antique White", "Ao (English)", "Apple Green", "Apricot", "Aqua", "Aquamarine", "Arctic Lime", "Army Green", "Arsenic", "Artichoke", "Arylide Yellow", "Ash Grey", "Asparagus", "Atomic Tangerine", "Auburn", "Aureolin", "AuroMetalSaurus", "Avocado", "Azure", "Azure (Web Color)", "Azure Mist", "Azureish White", "Baby Blue", "Baby Blue Eyes", "Baby Pink", "Baby Powder", "Baker-Miller Pink", "Ball Blue", "Banana Mania", "Banana Yellow", "Bangladesh Green", "Barbie Pink", "Barn Red", "Battleship Grey", "Bazaar", "Beau Blue", "Beaver", "Beige", "Bdazzled Blue", "Big Dip", "Bisque", "Bistre", "Bistre Brown", "Bitter Lemon", "Bitter Lime", "Bittersweet", "Bittersweet Shimmer", "Black", "Black Bean", "Black Leather Jacket", "Black Olive", "Blanched Almond", "Blast-Off Bronze", "Bleu De France", "Blizzard Blue", "Blond", "Blue", "Blue (Crayola)", "Blue (Munsell)", "Blue", "Blue (Pantone)", "Blue (Pigment)", "Blue", "Blue Bell", "Blue-Gray", "Blue-Green", "Blue Lagoon", "Blue-Magenta Violet", "Blue Sapphire", "Blue-Violet", "Blue Yonder", "Blueberry", "Bluebonnet", "Blush", "Bole", "Bondi Blue", "Bone", "Boston University Red", "Bottle Green", "Boysenberry", "Brandeis Blue", "Brass", "Brick Red", "Bright Cerulean", "Bright Green", "Bright Lavender", "Bright Lilac", "Bright Maroon", "Bright Navy Blue", "Bright Pink", "Bright Turquoise", "Bright Ube", "Brilliant Azure", "Brilliant Lavender", "Brilliant Rose", "Brink Pink", "British Racing Green", "Bronze", "Bronze Yellow", "Brown", "Brown", "Brown-Nose", "Brown Yellow", "Brunswick Green", "Bubble Gum", "Bubbles", "Buff", "Bud Green", "Bulgarian Rose", "Burgundy", "Burlywood", "Burnt Orange", "Burnt Sienna", "Burnt Umber", "Byzantine", "Byzantium", "Cadet", "Cadet Blue", "Cadet Grey", "Cadmium Green", "Cadmium Orange", "Cadmium Red", "Cadmium Yellow", "Café Au Lait", "Café Noir", "Cal Poly Green", "Cambridge Blue", "Camel", "Cameo Pink", "Camouflage Green", "Canary Yellow", "Candy Apple Red", "Candy Pink", "Capri", "Caput Mortuum", "Cardinal", "Caribbean Green", "Carmine", "Carmine", "Carmine Pink", "Carmine Red", "Carnation Pink", "Carnelian", "Carolina Blue", "Carrot Orange", "Castleton Green", "Catalina Blue", "Catawba", "Cedar Chest", "Ceil", "Celadon", "Celadon Blue", "Celadon Green", "Celeste", "Celestial Blue", "Cerise", "Cerise Pink", "Cerulean", "Cerulean Blue", "Cerulean Frost", "CG Blue", "CG Red", "Chamoisee", "Champagne", "Charcoal", "Charleston Green", "Charm Pink", "Chartreuse", "Chartreuse", "Cherry", "Cherry Blossom Pink", "Chestnut", "China Pink", "China Rose", "Chinese Red", "Chinese Violet", "Chocolate", "Chocolate", "Chrome Yellow", "Cinereous", "Cinnabar", "Cinnamon", "Citrine", "Citron", "Claret", "Classic Rose", "Cobalt Blue", "Cocoa Brown", "Coconut", "Coffee", "Columbia Blue", "Congo Pink", "Cool Black", "Cool Grey", "Copper", "Copper (Crayola)", "Copper Penny", "Copper Red", "Copper Rose", "Coquelicot", "Coral", "Coral Pink", "Coral Red", "Cordovan", "Corn", "Cornell Red", "Cornflower Blue", "Cornsilk", "Cosmic Latte", "Coyote Brown", "Cotton Candy", "Cream", "Crimson", "Crimson Glory", "Crimson Red", "Cyan", "Cyan Azure", "Cyan-Blue Azure", "Cyan Cobalt Blue", "Cyan Cornflower Blue", "Cyan (Process)", "Cyber Grape", "Cyber Yellow", "Daffodil", "Dandelion", "Dark Blue", "Dark Blue-Gray", "Dark Brown", "Dark Brown-Tangelo", "Dark Byzantium", "Dark Candy Apple Red", "Dark Cerulean", "Dark Chestnut", "Dark Coral", "Dark Cyan", "Dark Electric Blue", "Dark Goldenrod", "Dark Gray", "Dark Green", "Dark Green", "Dark Gunmetal", "Dark Imperial Blue", "Dark Imperial Blue", "Dark Jungle Green", "Dark Khaki", "Dark Lava", "Dark Lavender", "Dark Liver", "Dark Liver (Horses)", "Dark Magenta", "Dark Medium Gray", "Dark Midnight Blue", "Dark Moss Green", "Dark Olive Green", "Dark Orange", "Dark Orchid", "Dark Pastel Blue", "Dark Pastel Green", "Dark Pastel Purple", "Dark Pastel Red", "Dark Pink", "Dark Powder Blue", "Dark Puce", "Dark Purple", "Dark Raspberry", "Dark Red", "Dark Salmon", "Dark Scarlet", "Dark Sea Green", "Dark Sienna", "Dark Sky Blue", "Dark Slate Blue", "Dark Slate Gray", "Dark Spring Green", "Dark Tan", "Dark Tangerine", "Dark Taupe", "Dark Terra Cotta", "Dark Turquoise", "Dark Vanilla", "Dark Violet", "Dark Yellow", "Dartmouth Green", "Davy's Grey", "Debian Red", "Deep Aquamarine", "Deep Carmine", "Deep Carmine Pink", "Deep Carrot Orange", "Deep Cerise", "Deep Champagne", "Deep Chestnut", "Deep Coffee", "Deep Fuchsia", "Deep Green", "Deep Green-Cyan Turquoise", "Deep Jungle Green", "Deep Koamaru", "Deep Lemon", "Deep Lilac", "Deep Magenta", "Deep Maroon", "Deep Mauve", "Deep Moss Green", "Deep Peach", "Deep Pink", "Deep Puce", "Deep Red", "Deep Ruby", "Deep Saffron", "Deep Sky Blue", "Deep Space Sparkle", "Deep Spring Bud", "Deep Taupe", "Deep Tuscan Red", "Deep Violet", "Deer", "Denim", "Desaturated Cyan", "Desert", "Desert Sand", "Desire", "Diamond", "Dim Gray", "Dirt", "Dodger Blue", "Dogwood Rose", "Dollar Bill", "Donkey Brown", "Drab", "Duke Blue", "Dust Storm", "Dutch White", "Earth Yellow", "Ebony", "Ecru", "Eerie Black", "Eggplant", "Eggshell", "Egyptian Blue", "Electric Blue", "Electric Crimson", "Electric Cyan", "Electric Green", "Electric Indigo", "Electric Lavender", "Electric Lime", "Electric Purple", "Electric Ultramarine", "Electric Violet", "Electric Yellow", "Emerald", "Eminence", "English Green", "English Lavender", "English Red", "English Violet", "Eton Blue", "Eucalyptus", "Fallow", "Falu Red", "Fandango", "Fandango Pink", "Fashion Fuchsia", "Fawn", "Feldgrau", "Feldspar", "Fern Green", "Ferrari Red", "Field Drab", "Firebrick", "Fire Engine Red", "Flame", "Flamingo Pink", "Flattery", "Flavescent", "Flax", "Flirt", "Floral White", "Fluorescent Orange", "Fluorescent Pink", "Fluorescent Yellow", "Folly", "Forest Green", "Forest Green", "French Beige", "French Bistre", "French Blue", "French Fuchsia", "French Lilac", "French Lime", "French Mauve", "French Pink", "French Plum", "French Puce", "French Raspberry", "French Rose", "French Sky Blue", "French Violet", "French Wine", "Fresh Air", "Fuchsia", "Fuchsia (Crayola)", "Fuchsia Pink", "Fuchsia Purple", "Fuchsia Rose", "Fulvous", "Fuzzy Wuzzy"};
        String [] colorCodes1 = {"#B0BF1A", "#7CB9E8", "#C9FFE5", "#B284BE", "#5D8AA8", "#00308F", "#72A0C1", "#AF002A", "#F0F8FF", "#E32636", "#C46210", "#EFDECD", "#E52B50", "#AB274F", "#F19CBB", "#AB274F", "#D3212D", "#3B7A57", "#FFBF00", "#FF7E00", "#FF033E", "#9966CC", "#A4C639", "#F2F3F4", "#CD9575", "#665D1E", "#915C83", "#841B2D", "#FAEBD7", "#008000", "#8DB600", "#FBCEB1", "#00FFFF", "#7FFFD4", "#D0FF14", "#4B5320", "#3B444B", "#8F9779", "#E9D66B", "#B2BEB5", "#87A96B", "#FF9966", "#A52A2A", "#FDEE00", "#6E7F80", "#568203", "#007FFF", "#F0FFFF", "#F0FFFF", "#DBE9F4", "#89CFF0", "#A1CAF1", "#F4C2C2", "#FEFEFA", "#FF91AF", "#21ABCD", "#FAE7B5", "#FFE135", "#006A4E", "#E0218A", "#7C0A02", "#848482", "#98777B", "#BCD4E6", "#9F8170", "#F5F5DC", "#2E5894", "#9C2542", "#FFE4C4", "#3D2B1F", "#967117", "#CAE00D", "#BFFF00", "#FE6F5E", "#BF4F51", "#000000", "#3D0C02", "#253529", "#3B3C36", "#FFEBCD", "#A57164", "#318CE7", "#ACE5EE", "#FAF0BE", "#0000FF", "#1F75FE", "#0093AF", "#0087BD", "#0018A8", "#333399", "#0247FE", "#A2A2D0", "#6699CC", "#0D98BA", "#5E93A1", "#553592", "#126180", "#8A2BE2", "#5072A7", "#4F86F7", "#1C1CF0", "#DE5D83", "#79443B", "#0095B6", "#E3DAC9", "#CC0000", "#006A4E", "#873260", "#0070FF", "#B5A642", "#CB4154", "#1DACD6", "#66FF00", "#BF94E4", "#D891EF", "#C32148", "#1974D2", "#FF007F", "#08E8DE", "#D19FE8", "#3399FF", "#F4BBFF", "#FF55A3", "#FB607F", "#004225", "#CD7F32", "#737000", "#964B00", "#A52A2A", "#6B4423", "#cc9966", "#1B4D3E", "#FFC1CC", "#E7FEFF", "#F0DC82", "#7BB661", "#480607", "#800020", "#DEB887", "#CC5500", "#E97451", "#8A3324", "#BD33A4", "#702963", "#536872", "#5F9EA0", "#91A3B0", "#006B3C", "#ED872D", "#E30022", "#FFF600", "#A67B5B", "#4B3621", "#1E4D2B", "#A3C1AD", "#C19A6B", "#EFBBCC", "#78866B", "#FFEF00", "#FF0800", "#E4717A", "#00BFFF", "#592720", "#C41E3A", "#00CC99", "#960018", "#D70040", "#EB4C42", "#FF0038", "#FFA6C9", "#B31B1B", "#56A0D3", "#ED9121", "#00563F", "#062A78", "#703642", "#C95A49", "#92A1CF", "#ACE1AF", "#007BA7", "#2F847C", "#B2FFFF", "#4997D0", "#DE3163", "#EC3B83", "#007BA7", "#2A52BE", "#6D9BC3", "#007AA5", "#E03C31", "#A0785A", "#F7E7CE", "#36454F", "#232B2B", "#E68FAC", "#DFFF00", "#7FFF00", "#DE3163", "#FFB7C5", "#954535", "#DE6FA1", "#A8516E", "#AA381E", "#856088", "#7B3F00", "#D2691E", "#FFA700", "#98817B", "#E34234", "#D2691E", "#E4D00A", "#9FA91F", "#7F1734", "#FBCCE7", "#0047AB", "#D2691E", "#965A3E", "#6F4E37", "#C4D8E2", "#F88379", "#002E63", "#8C92AC", "#B87333", "#DA8A67", "#AD6F69", "#CB6D51", "#996666", "#FF3800", "#FF7F50", "#F88379", "#FF4040", "#893F45", "#FBEC5D", "#B31B1B", "#6495ED", "#FFF8DC", "#FFF8E7", "#81613e", "#FFBCD9", "#FFFDD0", "#DC143C", "#BE0032", "#990000", "#00FFFF", "#4E82b4", "#4682BF", "#28589C", "#188BC2", "#00B7EB", "#58427C", "#FFD300", "#FFFF31", "#F0E130", "#00008B", "#666699", "#654321", "#88654E", "#5D3954", "#A40000", "#08457E", "#986960", "#CD5B45", "#008B8B", "#536878", "#B8860B", "#A9A9A9 ","#013220", "#006400", "#006400", "#00416A", "#6E6EF9", "#1A2421", "#BDB76B", "#483C32", "#734F96", "#534B4F", "#543D37", "#8B008B", "#A9A9A9", "#003366", "#4A5D23", "#556B2F", "#FF8C00", "#9932CC", "#779ECB", "#03C03C", "#966FD6", "#C23B22", "#E75480", "#003399", "#4F3A3C", "#301934", "#872657", "#8B0000", "#E9967A", "#560319", "#8FBC8F", "#3C1414", "#8CBED6", "#483D8B", "#2F4F4F", "#177245", "#918151", "#FFA812", "#483C32", "#CC4E5C", "#00CED1", "#D1BEA8", "#9400D3", "#9B870C", "#00703C", "#555555", "#D70A53", "#40826D", "#A9203E", "#EF3038", "#E9692C", "#DA3287", "#FAD6A5", "#B94E48", "#704241", "#C154C1", "#056608", "#0E7C61", "#004B49", "#333366", "#F5C71A", "#9955BB", "#CC00CC", "#820000", "#D473D4", "#355E3B", "#FFCBA4", "#FF1493", "#A95C68", "#850101", "#843F5B", "#FF9933", "#00BFFF", "#4A646C", "#556B2F", "#7E5E60", "#66424D", "#330066", "#BA8759", "#1560BD", "#669999", "#C19A6B", "#EDC9AF", "#EA3C53", "#B9F2FF", "#696969", "#9B7653", "#1E90FF", "#D71868", "#85BB65", "#664C28", "#967117", "#00009C", "#E5CCC9", "#EFDFBB", "#E1A95F", "#555D50", "#C2B280", "#1B1B1B", "#614051", "#F0EAD6", "#1034A6", "#7DF9FF", "#FF003F", "#00FFFF", "#00FF00", "#6F00FF", "#F4BBFF", "#CCFF00", "#BF00FF", "#3F00FF", "#8F00FF", "#FFFF33", "#50C878", "#6C3082", "#1B4D3E", "#B48395", "#AB4B52", "#563C5C", "#96C8A2", "#44D7A8", "#C19A6B", "#801818", "#B53389", "#DE5285", "#F400A1", "#E5AA70", "#4D5D53", "#FDD5B1", "#4F7942", "#FF2800", "#6C541E", "#B22222", "#CE2029", "#E25822", "#FC8EAC", "#6B4423", "#F7E98E", "#EEDC82", "#A2006D", "#FFFAF0", "#FFBF00", "#FF1493", "#CCFF00", "#FF004F", "#014421", "#228B22", "#A67B5B", "#856D4D", "#0072BB", "#FD3F92", "#86608E", "#9EFD38", "#D473D4", "#FD6C9E", "#811453", "#4E1609", "#C72C48", "#F64A8A", "#77B5FE", "#8806CE", "#AC1E44", "#A6E7FF", "#FF00FF", "#C154C1", "#FF77FF", "#CC397B", "#C74375", "#E48400", "#CC6666"};

        String [] colorNames2 = {"Gainsboro", "Gamboge", "Gamboge Orange (Brown)", "Generic Viridian", "Ghost White", "Giants Orange", "Glaucous", "Glitter", "GO Green", "Gold (Metallic)", "Gold (Golden)", "Gold Fusion", "Golden Brown", "Golden Poppy", "Golden Yellow", "Goldenrod", "Granny Smith Apple", "Grape", "Grussrel", "Gray", "Gray", "Gray", "Gray-Asparagus", "Gray-Blue", "Green", "Green (Crayola)", "Green", "Green (Munsell)", "Green", "Green (Pantone)", "Green (Pigment)", "Green", "Green-Blue", "Green-Cyan", "Green-Yellow", "Grizzly", "Grullo", "Guppie Green", "Gunmetal", "Halayà Úbe", "Han Blue", "Han Purple", "Hansa Yellow", "Harlequin", "Harlequin Green", "Harvard Crimson", "Harvest Gold", "Heart Gold", "Heliotrope", "Heliotrope Gray", "Heliotrope Magenta", "Hollywood Cerise", "Honeydew", "Honolulu Blue", "Hooker's Green", "Hot Magenta", "Hot Pink", "Hunter Green", "Iceberg", "Icterine", "Illuminating Emerald", "Imperial", "Imperial Blue", "Imperial Purple", "Imperial Red", "Inchworm", "Independence", "India Green", "Indian Red", "Indian Yellow", "Indigo", "Indigo Dye", "Indigo", "International Klein Blue", "International Orange (Aerospace)", "International Orange (Engineering)", "International Orange (Golden Gate Bridge)", "Iris", "Irresistible", "Isabelline", "Islamic Green", "Italian Sky Blue", "Ivory", "Jade", "Japanese Carmine", "Japanese Indigo", "Japanese Violet", "Jasmine", "Jasper", "Jazzberry Jam", "Jelly Bean", "Jet", "Jonquil", "Jordy Blue", "June Bud", "Jungle Green", "Kelly Green", "Kenyan Copper", "Keppel", "Jawad/Chicken Color(Khaki)", "Khaki", "Kobe", "Kobi", "Kombu Green", "KU Crimson", "La Salle Green", "Languid Lavender", "Lapis Lazuli", "Laser Lemon", "Laurel Green", "Lava", "Lavender", "Lavender", "Lavender Blue", "Lavender Blush", "Lavender Gray", "Lavender Indigo", "Lavender Magenta", "Lavender Mist", "Lavender Pink", "Lavender Purple", "Lavender Rose", "Lawn Green", "Lemon", "Lemon Chiffon", "Lemon Curry", "Lemon Glacier", "Lemon Lime", "Lemon Meringue", "Lemon Yellow", "Lenurple", "Licorice", "Liberty", "Light Apricot", "Light Blue", "Light Brilliant Red", "Light Brown", "Light Carmine Pink", "Light Cobalt Blue", "Light Coral", "Light Cornflower Blue", "Light Crimson", "Light Cyan", "Light Deep Pink", "Light French Beige", "Light Fuchsia Pink", "Light Goldenrod Yellow", "Light Gray", "Light Grayish Magenta", "Light Green", "Light Hot Pink", "Light Khaki", "Light Medium Orchid", "Light Moss Green", "Light Orchid", "Light Pastel Purple", "Light Pink", "Light Red Ochre", "Light Salmon", "Light Salmon Pink", "Light Sea Green", "Light Sky Blue", "Light Slate Gray", "Light Steel Blue", "Light Taupe", "Light Thulian Pink", "Light Yellow", "Lilac", "Lime", "Lime", "Lime Green", "Limerick", "Lincoln Green", "Linen", "Lion", "Liseran Purple", "Little Boy Blue", "Liver", "Liver (Dogs)", "Liver (Organ)", "Liver Chestnut", "Livid", "Lumber", "Lust", "Magenta", "Magenta (Crayola)", "Magenta (Dye)", "Magenta (Pantone)", "Magenta", "Magenta Haze", "Magenta-Pink", "Magic Mint", "Magnolia", "Mahogany", "Maize", "Majorelle Blue", "Malachite", "Manatee", "Mango Tango", "Mantis", "Mardi Gras", "Marigold", "Maroon (Crayola)", "Maroon", "Maroon", "Mauve", "Mauve Taupe", "Mauvelous", "May Green", "Maya Blue", "Meat Brown", "Medium Aquamarine", "Medium Blue", "Medium Candy Apple Red", "Medium Carmine", "Medium Champagne", "Medium Electric Blue", "Medium Jungle Green", "Medium Lavender Magenta", "Medium Orchid", "Medium Persian Blue", "Medium Purple", "Medium Red-Violet", "Medium Ruby", "Medium Sea Green", "Medium Sky Blue", "Medium Slate Blue", "Medium Spring Bud", "Medium Spring Green", "Medium Taupe", "Medium Turquoise", "Medium Tuscan Red", "Medium Vermilion", "Medium Violet-Red", "Mellow Apricot", "Mellow Yellow", "Melon", "Metallic Seaweed", "Metallic Sunburst", "Mexican Pink", "Midnight Blue", "Midnight Green (Eagle Green)", "Mikado Yellow", "Mindaro", "Ming", "Mint", "Mint Cream", "Mint Green", "Misty Rose", "Moccasin", "Mode Beige", "Moonstone Blue", "Mordant Red 19", "Moss Green", "Mountain Meadow", "Mountbatten Pink", "MSU Green", "Mughal Green", "Mulberry", "Mustard", "Myrtle Green"};
        String [] colorCodes2 = {"#DCDCDC", "#E49B0F", "#996600", "#007F66", "#F8F8FF", "#FE5A1D", "#6082B6", "#E6E8FA", "#00AB66", "#D4AF37", "#FFD700", "#85754E", "#996515", "#FCC200", "#FFDF00", "#DAA520", "#A8E4A0", "#6F2DA8", "#B06500", "#808080", "#808080", "#BEBEBE", "#465945", "#8C92AC", "#00FF00", "#1CAC78", "#008000", "#00A877", "#009F6B", "#00AD43", "#00A550", "#66B032", "#1164B4", "#009966", "#ADFF2F", "#885818", "#A99A86", "#00FF7F", "#2a3439", "#663854", "#446CCF", "#5218FA", "#E9D66B", "#3FFF00", "#46CB18", "#C90016", "#DA9100", "#808000", "#DF73FF", "#AA98A9", "#AA00BB", "#F400A1", "#F0FFF0", "#006DB0", "#49796B", "#FF1DCE", "#FF69B4", "#355E3B", "#71A6D2", "#FCF75E", "#319177", "#602F6B", "#002395", "#66023C", "#ED2939", "#B2EC5D", "#4C516D", "#138808", "#CD5C5C", "#E3A857", "#6F00FF", "#091F92", "#4B0082", "#002FA7", "#FF4F00", "#BA160C", "#C0362C", "#5A4FCF", "#B3446C", "#F4F0EC", "#009000", "#B2FFFF", "#FFFFF0", "#00A86B", "#9D2933", "#264348", "#5B3256", "#F8DE7E", "#D73B3E", "#A50B5E", "#DA614E", "#343434", "#F4CA16", "#8AB9F1", "#BDDA57", "#29AB87", "#4CBB17", "#7C1C05", "#3AB09E", "#C3B091", "#F0E68C", "#882D17", "#E79FC4", "#354230", "#E8000D", "#087830", "#D6CADD", "#26619C", "#FFFF66", "#A9BA9D", "#CF1020", "#B57EDC", "#E6E6FA", "#CCCCFF", "#FFF0F5", "#C4C3D0", "#9457EB", "#EE82EE", "#E6E6FA", "#FBAED2", "#967BB6", "#FBA0E3", "#7CFC00", "#FFF700", "#FFFACD", "#CCA01D", "#FDFF00", "#E3FF00", "#F6EABE", "#FFF44F", "#BA93D8", "#1A1110", "#545AA7", "#FDD5B1", "#ADD8E6", "#fe2e2e", "#B5651D", "#E66771", "#88ACE0", "#F08080", "#93CCEA", "#F56991", "#E0FFFF", "#FF5CCD", "#C8AD7F", "#F984EF", "#FAFAD2", "#D3D3D3", "#CC99CC", "#90EE90", "#FFB3DE", "#F0E68C", "#D39BCB", "#ADDFAD", "#E6A8D7", "#B19CD9", "#FFB6C1", "#E97451", "#FFA07A", "#FF9999", "#20B2AA", "#87CEFA", "#778899", "#B0C4DE", "#B38B6D", "#E68FAC", "#FFFFE0", "#C8A2C8", "#BFFF00", "#00FF00", "#32CD32", "#9DC209", "#195905", "#FAF0E6", "#C19A6B", "#DE6FA1", "#6CA0DC", "#674C47", "#B86D29", "#6C2E1F", "#987456", "#6699CC", "#FFE4CD", "#E62020", "#FF00FF", "#FF55A3", "#CA1F7B", "#D0417E", "#FF0090", "#9F4576", "#CC338B", "#AAF0D1", "#F8F4FF", "#C04000", "#FBEC5D", "#6050DC", "#0BDA51", "#979AAA", "#FF8243", "#74C365", "#880085", "#EAA221", "#C32148", "#800000", "#B03060", "#E0B0FF", "#915F6D", "#EF98AA", "#4C9141", "#73C2FB", "#E5B73B", "#66DDAA", "#0000CD", "#E2062C", "#AF4035", "#F3E5AB", "#035096", "#1C352D", "#DDA0DD", "#BA55D3", "#0067A5", "#9370DB", "#BB3385", "#AA4069", "#3CB371", "#80DAEB", "#7B68EE", "#C9DC87", "#00FA9A", "#674C47", "#48D1CC", "#79443B", "#D9603B", "#C71585", "#F8B878", "#F8DE7E", "#FDBCB4", "#0A7E8C", "#9C7C38", "#E4007C", "#191970", "#004953", "#FFC40C", "#E3F988", "#36747D", "#3EB489", "#F5FFFA", "#98FF98", "#FFE4E1", "#FAEBD7", "#967117", "#73A9C2", "#AE0C00", "#8A9A5B", "#30BA8F", "#997A8D", "#18453B", "#306030", "#C54B8C", "#FFDB58", "#317873"};

        String [] colorNames3 = {"Nadeshiko Pink", "Napier Green", "Naples Yellow", "Navajo White", "Navy", "Navy Purple", "Neon Carrot", "Neon Fuchsia", "Neon Green", "New Car", "New York Pink", "Non-Photo Blue", "North Texas Green", "Nyanza", "Ocean Boat Blue", "Ochre", "Office Green", "Old Burgundy", "Old Gold", "Old Heliotrope", "Old Lace", "Old Lavender", "Old Mauve", "Old Moss Green", "Old Rose", "Old Silver", "Olive", "Olive Drab", "Olive Drab", "Olivine", "Onyx", "Opera Mauve", "Orange (Color Wheel)", "Orange (Crayola)", "Orange (Pantone)","Orange", "Orange", "Orange Peel", "Orange-Red", "Orange-Yellow", "Orchid", "Orchid Pink", "Orioles Orange", "Otter Brown", "Outer Space", "Outrageous Orange", "Oxford Blue", "OU Crimson Red", "Pakistan Green", "Palatinate Blue", "Palatinate Purple", "Pale Aqua", "Pale Blue", "Pale Brown", "Pale Carmine", "Pale Cerulean", "Pale Chestnut", "Pale Copper", "Pale Cornflower Blue", "Pale Cyan", "Pale Gold", "Pale Goldenrod", "Pale Green", "Pale Lavender", "Pale Magenta", "Pale Magenta-Pink", "Pale Pink", "Pale Plum", "Pale Red-Violet", "Pale Robin Egg Blue", "Pale Silver", "Pale Spring Bud", "Pale Taupe", "Pale Turquoise", "Pale Violet", "Pale Violet-Red", "Pansy Purple", "Paolo Veronese Green", "Papaya Whip", "Paradise Pink", "Paris Green", "Pastel Blue", "Pastel Brown", "Pastel Gray", "Pastel Green", "Pastel Magenta", "Pastel Orange", "Pastel Pink", "Pastel Purple", "Pastel Red", "Pastel Violet", "Pastel Yellow", "Patriarch", "Payne's Grey", "Peach", "Peach", "Peach-Orange", "Peach Puff", "Peach-Yellow", "Pear", "Pearl", "Pearl Aqua", "Pearly Purple", "Peridot", "Periwinkle", "Persian Blue", "Persian Green", "Persian Indigo", "Persian Orange", "Persian Pink", "Persian Plum", "Persian Red", "Persian Rose", "Persimmon", "Peru", "Phlox", "Phthalo Blue", "Phthalo Green", "Picton Blue", "Pictorial Carmine", "Piggy Pink", "Pine Green", "Pineapple", "Pink", "Pink (Pantone)", "Pink Lace", "Pink Lavender", "Pink-Orange", "Pink Pearl", "Pink Raspberry", "Pink Sherbet", "Pistachio", "Platinum", "Plum", "Plum", "Pomp And Power", "Popstar", "Portland Orange", "Powder Blue", "Princeton Orange", "Prune", "Prussian Blue", "Psychedelic Purple", "Puce", "Puce Red", "Pullman Brown (UPS Brown)", "Pullman Green", "Pumpkin", "Purple", "Purple (Munsell)", "Purple", "Purple Heart", "Purple Mountain Majesty", "Purple Navy", "Purple Pizzazz", "Purple Taupe", "Purpureus", "Quartz", "Queen Blue", "Queen Pink", "Quinacridone Magenta", "Rackley", "Radical Red", "Rajah", "Raspberry", "Raspberry Glace", "Raspberry Pink", "Raspberry Rose", "Raw Umber", "Razzle Dazzle Rose", "Razzmatazz", "Razzmic Berry", "Rebecca Purple", "Red", "Red (Crayola)", "Red (Munsell)", "Red", "Red", "Red", "Red", "Red-Brown", "Red Devil", "Red-Orange", "Red-Purple", "Red-Violet", "Redwood", "Regalia", "Registration Black", "Resolution Blue", "Rhythm", "Rich Black", "Rich Black", "Rich Black", "Rich Brilliant Lavender", "Rich Carmine", "Rich Electric Blue", "Rich Lavender", "Rich Lilac", "Rich Maroon", "Rifle Green", "Roast Coffee", "Robin Egg Blue", "Rocket Metallic", "Roman Silver", "Rose", "Rose Bonbon", "Rose Ebony", "Rose Gold", "Rose Madder", "Rose Pink", "Rose Quartz", "Rose Red", "Rose Taupe", "Rose Vale", "Rosewood", "Rosso Corsa", "Rosy Brown", "Royal Azure", "Royal Blue", "Royal Blue", "Royal Fuchsia", "Royal Purple", "Royal Yellow", "Ruber", "Rubine Red", "Ruby", "Ruby Red", "Ruddy", "Ruddy Brown", "Ruddy Pink", "Rufous", "Russet", "Russian Green", "Russian Violet", "Rust", "Rusty Red", "Sacramento State Green", "Saddle Brown", "Safety Orange", "Safety Orange (Blaze Orange)", "Safety Yellow", "Saffron", "Sage", "St. Patrick's Blue", "Salmon", "Salmon Pink", "Sand", "Sand Dune", "Sandstorm", "Sandy Brown", "Sandy Taupe", "Sangria", "Sap Green", "Sapphire", "Sapphire Blue", "Satin Sheen Gold", "Scarlet", "Scarlet", "Schauss Pink", "School Bus Yellow", "Screaming Green", "Sea Blue", "Sea Green", "Seal Brown", "Seashell", "Selective Yellow", "Sepia", "Shadow", "Shadow Blue", "Shampoo", "Shamrock Green", "Sheen Green", "Shimmering Blush", "Shocking Pink", "Shocking Pink (Crayola)", "Sienna", "Silver", "Silver Chalice", "Silver Lake Blue", "Silver Pink", "Silver Sand", "Sinopia", "Skobeloff", "Sky Blue", "Sky Magenta", "Slate Blue", "Slate Gray", "Smalt (Dark Powder Blue)", "Smitten", "Smoke", "Smoky Black", "Smoky Topaz", "Snow", "Soap", "Solid Pink", "Sonic Silver", "Spartan Crimson", "Space Cadet", "Spanish Bistre", "Spanish Blue", "Spanish Carmine", "Spanish Crimson", "Spanish Gray", "Spanish Green", "Spanish Orange", "Spanish Pink", "Spanish Red", "Spanish Sky Blue", "Spanish Violet", "Spanish Viridian", "Spicy Mix", "Spiro Disco Ball", "Spring Bud", "Spring Green", "Star Command Blue", "Steel Blue", "Steel Pink", "Stil De Grain Yellow", "Stizza", "Stormcloud", "Straw", "Strawberry", "Sunglow", "Sunray", "Sunset", "Sunset Orange", "Super Pink", "Tan", "Tangelo", "Tangerine", "Tangerine Yellow", "Tango Pink", "Taupe", "Taupe Gray", "Tea Green", "Tea Rose", "Tea Rose", "Teal", "Teal Blue", "Teal Deer", "Teal Green", "Telemagenta", "Tenné", "Terra Cotta", "Thistle", "Thulian Pink", "Tickle Me Pink", "Tiffany Blue", "Tiger's Eye", "Timberwolf", "Titanium Yellow", "Tomato", "Toolbox", "Topaz", "Tractor Red", "Trolley Grey", "Tropical Rain Forest", "True Blue", "Tufts Blue", "Tulip", "Tumbleweed", "Turkish Rose", "Turquoise", "Turquoise Blue", "Turquoise Green", "Tuscan", "Tuscan Brown", "Tuscan Red", "Tuscan Tan", "Tuscany", "Twilight Lavender", "Tyrian Purple", "UA Blue", "UA Red", "Ube", "UCLA Blue", "UCLA Gold", "UFO Green", "Ultramarine", "Ultramarine Blue", "Ultra Pink", "Ultra Red", "Umber", "Unbleached Silk", "United Nations Blue", "University Of California Gold", "Unmellow Yellow", "UP Forest Green", "UP Maroon", "Upsdell Red", "Urobilin", "USAFA Blue", "USC Cardinal", "USC Gold", "University Of Tennessee Orange", "Utah Crimson", "Vanilla", "Vanilla Ice", "Vegas Gold", "Venetian Red", "Verdigris", "Vermilion", "Vermilion", "Veronica", "Very Light Azure", "Very Light Blue", "Very Light Malachite Green", "Very Light Tangelo", "Very Pale Orange", "Very Pale Yellow", "Violet", "Violet (Color Wheel)", "Violet", "Violet", "Violet-Blue", "Violet-Red", "Viridian", "Viridian Green", "Vista Blue", "Vivid Amber", "Vivid Auburn", "Vivid Burgundy", "Vivid Cerise", "Vivid Cerulean", "Vivid Crimson", "Vivid Gamboge", "Vivid Lime Green", "Vivid Malachite", "Vivid Mulberry", "Vivid Orange", "Vivid Orange Peel", "Vivid Orchid", "Vivid Raspberry", "Vivid Red", "Vivid Red-Tangelo", "Vivid Sky Blue", "Vivid Tangelo", "Vivid Tangerine", "Vivid Vermilion", "Vivid Violet", "Vivid Yellow", "Volt", "Warm Black", "Waterspout", "Wenge", "Wheat", "White", "White Smoke", "Wild Blue Yonder", "Wild Orchid", "Wild Strawberry", "Wild Watermelon", "Willpower Orange", "Windsor Tan", "Wine", "Wine Dregs", "Wisteria", "Wood Brown", "Xanadu", "Yale Blue", "Yankees Blue", "Yellow", "Yellow (Crayola)", "Yellow (Munsell)", "Yellow", "Yellow (Pantone)", "Yellow (Process)", "Yellow", "Yellow-Green", "Yellow Orange", "Yellow Rose", "Zaffre", "Zinnwaldite Brown", "Zomp"};
        String [] colorCodes3 = {"#F6ADC6", "#2A8000", "#FADA5E", "#FFDEAD", "#000080", "#9457EB", "#FFA343", "#FE4164", "#39FF14", "#214FC6", "#D7837F", "#A4DDED", "#059033", "#E9FFDB", "#0077BE", "#CC7722", "#008000", "#43302E", "#CFB53B", "#563C5C", "#FDF5E6", "#796878", "#673147", "#867E36", "#C08081", "#848482", "#808000", "#6B8E23", "#3C341F", "#9AB973", "#353839", "#B784A7", "#FF7F00", "#FF7538", "#FF5800", "#FB9902", "#FFA500", "#FF9F00", "#FF4500", "#F8D568", "#DA70D6", "#F2BDCD", "#FB4F14", "#654321", "#414A4C", "#FF6E4A", "#002147", "#990000", "#006600", "#273BE2", "#682860", "#BCD4E6", "#AFEEEE", "#987654", "#AF4035", "#9BC4E2", "#DDADAF", "#DA8A67", "#ABCDEF", "#87D3F8", "#E6BE8A", "#EEE8AA", "#98FB98", "#DCD0FF", "#F984E5", "#ff99cc", "#FADADD", "#DDA0DD", "#DB7093", "#96DED1", "#C9C0BB", "#ECEBBD", "#BC987E", "#AFEEEE", "#CC99FF", "#DB7093", "#78184A", "#009B7D", "#FFEFD5", "#E63E62", "#50C878", "#AEC6CF", "#836953", "#CFCFC4", "#77DD77", "#F49AC2", "#FFB347", "#DEA5A4", "#B39EB5", "#FF6961", "#CB99C9", "#FDFD96", "#800080", "#536878", "#FFE5B4", "#FFCBA4", "#FFCC99", "#FFDAB9", "#FADFAD", "#D1E231", "#EAE0C8", "#88D8C0", "#B768A2", "#E6E200", "#CCCCFF", "#1C39BB", "#00A693", "#32127A", "#D99058", "#F77FBE", "#701C1C", "#CC3333", "#FE28A2", "#EC5800", "#CD853F", "#DF00FF", "#000F89", "#123524", "#45B1E8", "#C30B4E", "#FDDDE6", "#01796F", "#563C5C", "#FFC0CB", "#D74894", "#FFDDF4", "#D8B2D1", "#FF9966", "#E7ACCF", "#980036", "#F78FA7", "#93C572", "#E5E4E2", "#8E4585", "#DDA0DD", "#86608E", "#BE4F62", "#FF5A36", "#B0E0E6", "#F58025", "#701C1C", "#003153", "#DF00FF", "#CC8899", "#722F37", "#644117", "#3B331C", "#FF7518", "#800080", "#9F00C5", "#A020F0", "#69359C", "#9678B6", "#4E5180", "#FE4EDA", "#50404D", "#9A4EAE", "#51484F", "#436B95", "#E8CCD7", "#8E3A59", "#5D8AA8", "#FF355E", "#FBAB60", "#E30B5D", "#915F6D", "#E25098", "#B3446C", "#826644", "#FF33CC", "#E3256B", "#8D4E85", "#663399", "#FF0000", "#EE204D", "#F2003C", "#C40233", "#ED2939", "#ED1C24", "#FE2712", "#A52A2A", "#860111", "#FF5349", "#E40078", "#C71585", "#A45A52", "#522D80", "#000000", "#002387", "#777696", "#004040", "#010B13", "#010203", "#F1A7FE", "#D70040", "#0892D0", "#A76BCF", "#B666D2", "#B03060", "#444C38", "#704241", "#00CCCC", "#8A7F80", "#838996", "#FF007F", "#F9429E", "#674846", "#B76E79", "#E32636", "#FF66CC", "#AA98A9", "#C21E56", "#905D5D", "#AB4E52", "#65000B", "#D40000", "#BC8F8F", "#0038A8", "#002366", "#4169E1", "#CA2C92", "#7851A9", "#FADA5E", "#CE4676", "#D10056", "#E0115F", "#9B111E", "#FF0028", "#BB6528", "#E18E96", "#A81C07", "#80461B", "#679267", "#32174D", "#B7410E", "#DA2C43", "#00563F", "#8B4513", "#FF7800", "#FF6700", "#EED202", "#F4C430", "#BCB88A", "#23297A", "#FA8072", "#FF91A4", "#C2B280", "#967117", "#ECD540", "#F4A460", "#967117", "#92000A", "#507D2A", "#0F52BA", "#0067A5", "#CBA135", "#FF2400", "#FD0E35", "#FF91AF", "#FFD800", "#76FF7A", "#006994", "#2E8B57", "#59260B", "#FFF5EE", "#FFBA00", "#704214", "#8A795D", "#778BA5", "#FFCFF1", "#009E60", "#8FD400", "#D98695", "#FC0FC0", "#FF6FFF", "#882D17", "#C0C0C0", "#ACACAC", "#5D89BA", "#C4AEAD", "#BFC1C2", "#CB410B", "#007474", "#87CEEB", "#CF71AF", "#6A5ACD", "#708090", "#003399", "#C84186", "#738276", "#100C08", "#933D41", "#FFFAFA", "#CEC8EF", "#893843", "#757575", "#9E1316", "#1D2951", "#807532", "#0070B8", "#D10047", "#E51A4C", "#989898", "#009150", "#E86100", "#F7BFBE", "#E60026", "#00FFFF", "#4C2882", "#007F5C", "#8B5f4D", "#0FC0FC", "#A7FC00", "#00FF7F", "#007BB8", "#4682B4", "#CC33CC", "#FADA5E", "#990000", "#4F666A", "#E4D96F", "#FC5A8D", "#FFCC33", "#E3AB57", "#FAD6A5", "#FD5E53", "#CF6BA9", "#D2B48C", "#F94D00", "#F28500", "#FFCC00", "#E4717A", "#483C32", "#8B8589", "#D0F0C0", "#F88379", "#F4C2C2", "#008080", "#367588", "#99E6B3", "#00827F", "#CF3476", "#CD5700", "#E2725B", "#D8BFD8", "#DE6FA1", "#FC89AC", "#0ABAB5", "#E08D3C", "#DBD7D2", "#EEE600", "#FF6347", "#746CC0", "#FFC87C", "#FD0E35", "#808080", "#00755E", "#0073CF", "#417DC1", "#FF878D", "#DEAA88", "#B57281", "#40E0D0", "#00FFEF", "#A0D6B4", "#FAD6A5", "#6F4E37", "#7C4848", "#A67B5B", "#C09999", "#8A496B", "#66023C", "#0033AA", "#D9004C", "#8878C3", "#536895", "#FFB300", "#3CD070", "#3F00FF", "#4166F5", "#FF6FFF", "#FC6C85", "#635147", "#FFDDCA", "#5B92E5", "#B78727", "#FFFF66", "#014421", "#7B1113", "#AE2029", "#E1AD21", "#004F98", "#990000", "#FFCC00", "#F77F00", "#D3003F", "#F3E5AB", "#F38FA9", "#C5B358", "#C80815", "#43B3AE", "#E34234", "#D9381E", "#A020F0", "#74BBFB", "#6666FF", "#64E986", "#FFB077", "#FFDFBF", "#FFFFBF", "#8F00FF", "#7F00FF", "#8601AF", "#EE82EE", "#324AB2", "#F75394", "#40826D", "#009698", "#7C9ED9", "#cc9900", "#922724", "#9F1D35", "#DA1D81", "#00aaee", "#CC0033", "#FF9900", "#a6d608", "#00cc33", "#B80CE3", "#FF5F00", "#FFA000", "#CC00FF", "#FF006C", "#F70D1A", "#DF6124", "#00CCFF", "#F07427", "#FFA089", "#e56024", "#9F00FF", "#FFE302", "#CEFF00", "#004242", "#A4F4F9", "#645452", "#F5DEB3", "#FFFFFF", "#F5F5F5", "#A2ADD0", "#D470A2", "#FF43A4", "#FC6C85", "#FD5800", "#A75502", "#722F37", "#673147", "#C9A0DC", "#C19A6B", "#738678", "#0F4D92", "#1C2841", "#FFFF00", "#FCE883", "#EFCC00", "#FFD300", "#FEDF00", "#FFEF00", "#FEFE33", "#9ACD32", "#FFAE42", "#FFF000", "#0014A8", "#2C1608", "#39A78E"};

        int nameLen1 = colorNames1.length;
        int nameLen2 = colorNames2.length;
        int nameLen3 = colorNames3.length;

        for(i=0; i<nameLen1; i++)
        {
            ContentValues values = new ContentValues();
            values.put(ColorProperties.KEY_name, colorNames1[i]);
            values.put(ColorProperties.KEY_code, colorCodes1[i]);

            db.insert(ColorProperties.TABLE, null, values);

        }

        for(i=0; i<nameLen2; i++)
        {
            ContentValues values = new ContentValues();
            values.put(ColorProperties.KEY_name, colorNames2[i]);
            values.put(ColorProperties.KEY_code, colorCodes2[i]);

            db.insert(ColorProperties.TABLE, null, values);

        }

        for(i=0; i<nameLen3; i++)
        {
            ContentValues values = new ContentValues();
            values.put(ColorProperties.KEY_name, colorNames3[i]);
            values.put(ColorProperties.KEY_code, colorCodes3[i]);

            db.insert(ColorProperties.TABLE, null, values);

        }
    }


    public ArrayList<HashMap<String, String>> getColorList() {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                ColorProperties.KEY_ID + "," +
                ColorProperties.KEY_name + "," +
                ColorProperties.KEY_code +
                " FROM " + ColorProperties.TABLE;

        ArrayList<HashMap<String, String>> colorList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> color = new HashMap<String, String>();
                color.put("id", cursor.getString(cursor.getColumnIndex(ColorProperties.KEY_ID)));
                color.put("name", cursor.getString(cursor.getColumnIndex(ColorProperties.KEY_name)));
                color.put("code", cursor.getString(cursor.getColumnIndex(ColorProperties.KEY_code)));
                colorList.add(color);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return colorList;

    }

    public ColorProperties getColorByCode(String code){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                ColorProperties.KEY_ID + "," +
                ColorProperties.KEY_name + "," +
                ColorProperties.KEY_code +
                " FROM " + ColorProperties.TABLE
                + " WHERE " +
                ColorProperties.KEY_code + " = ?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        ColorProperties color = new ColorProperties();

        Cursor cursor = db.rawQuery(selectQuery,new String[] {code});

        // 3. if we got results get the first one
        if (cursor != null && cursor.moveToFirst()) {

            // 4. build color object
            color.colorID = cursor.getInt(0);
            color.colorName = cursor.getString(1);
            color.colorCode = cursor.getString(2);
            cursor.close();
        }
        else
        {
            String name =findClosedColor(code);
            if(name == null)
            {
                color = null;
                Log.w("myApp", "Yakın bir renk bulunamadı!");
            }
            else
            {
                Log.w("myApp", "HashMap'ten gelen renk:" + name);
                color.colorName = name;
            }
        }

        db.close();
        return color;
    }

    private String findClosedColor(String hexColor) {
        int rgb[] = hexToRGB(hexColor);
        int min = 3 * (int) pow(256, 2) + 1;
        ArrayList<HashMap<String, String>> colorList = getColorList();

        String colorName = null;
        int i;
        int len = colorList.size();
        for (i = 0; i < len; i++) {
            HashMap<String, String> map = colorList.get(i);
            String colorCode = map.get("code");
            Log.w("myApp", "HashMap'ten gelen colorCode:" + colorCode);
            if (colorCode != null) {
                int df = rgbDistance(hexToRGB(colorCode), rgb);
                if (df < min) {
                    min = df;
                    colorName = map.get("name");
                }
            }
        }
        return colorName;
    }

    private int rgbDistance(int[] c1, int[] c2) {
        return ( (int) pow(c1[0] - c2[0], 2)) + ((int) pow(c1[1] - c2[1], 2)) + ((int) pow(c1[2] - c2[2], 2));
    }

    private int[] hexToRGB( String hexCode)
    {
        int returnValue[] = new int[3];

        if (hexCode.charAt(0) == '#')
        {
            hexCode = hexCode.substring(1);
        }

        if (hexCode.length() < 6)
        {
            returnValue[0] = -1;
            returnValue[1] = -1;
            returnValue[2] = -1;
        }
        else
        {
            int r = fromHex(hexCode.substring(0, 2));
            int g = fromHex(hexCode.substring(2, 4));
            int b = fromHex(hexCode.substring(4, 6));

            returnValue[0] = r;
            returnValue[1] = g;
            returnValue[2] = b;

        }
        return returnValue;
    }

    private int fromHex( String n) {
        n = n.toUpperCase();
        if (n.length() < 2)
            return -1;
        int f1 = letterToCode(n.charAt(0));
        int f2 = letterToCode(n.charAt(1));
        if (f1 == -1 || f2 == -1) {
            return -1;
        } else {
            return f1 * 16 + f2;
        }
    }

    private int letterToCode(char n) {
        switch (n) {
            case '0': return 0;
            case '1': return 1;
            case '2': return 2;
            case '3': return 3;
            case '4': return 4;
            case '5': return 5;
            case '6': return 6;
            case '7': return 7;
            case '8': return 8;
            case '9': return 9;
            case 'A': return 10;
            case 'B': return 11;
            case 'C': return 12;
            case 'D': return 13;
            case 'E': return 14;
            case 'F': return 15;
            default: return -1;
        }
    }

}
