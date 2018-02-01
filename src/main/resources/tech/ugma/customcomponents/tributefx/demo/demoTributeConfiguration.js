var tribute = new Tribute({
  values: [],
  selectTemplate: function(person) {
    return '<span contenteditable="false" id="mention" email="' + person.original.email +
    '" title="' + person.original.email + '">' + this.current.collection.trigger
    + person.original[this.current.collection.fillAttr] + '</span>'
  },
  allowSpaces: true,
  replaceTextSuffix: '',
  fillAttr: 'key'
});

var container = document.getElementById('tributable-container');
tribute.attach(container);