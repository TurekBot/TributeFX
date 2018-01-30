alert("JavaScript is working");
document.write("JavaScript is working");

var tribute = new Tribute({
  values: [],

/*TODO: Just put first name*/
/*TODO: Hide email and specialness within for later parsing*/
  selectTemplate: function (item) {
      return '<span class="mention" contenteditable="false">@' + item.original.key + '</span>';
    }
});

var mentionableContainers = document.getElementsByClassName("mentionable-container");
var i;
for (i = 0; i < mentionableContainers.length; i++) {
    tribute.attach(mentionableContainers[i]);
}
