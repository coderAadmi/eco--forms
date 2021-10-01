package ecofrost.app.dynamic.form.zen

/**
 * id -> id of the object
 * type -> type of view
 * value -> if null or len =0 i.e., not filled or if text then set it on field
 * isMandatory -> specifies if it's mandatory or not
 * pattern -> for matching the regex
 * dependsUpon -> if = -1 -> false , otherwise, specifies the id of the view that'll be the source for it
 * options -> key to fetch values from stored list in hashmap
 * images -> to show images captured
 */

data class FO (
    val id : Int,
    val type : Int,
    val title : String,
    val placeholder : String?,
    val value : String?,
    val isMandatory : Boolean,
    val pattern : String?,
    val dependsUpon : Int,
    val options : String?,
    val images : List<String>?
)