//This text will show until the user focuses the container.
var PROMPT_TEXT = 'PROMPT_TEXT_WILL_BE_REPLACED_HERE';

//Get the container for later use
var container = document.getElementById('tributable-container');

//Removes the prompt text from the container
function removePromptText() {
  var promptText = document.getElementById('prompt-text');

  if (promptText != null) {
    container.removeChild(promptText);
    console.log("Removed prompt text.");
  } else {
    console.log("Tried to remove the prompt text; couldn't find it.")
  }
};

//Adds the prompt text to the container
function addPromptText() {
  if (!container.hasChildNodes()) {
    if (document.getElementById('prompt-text') == null) {
      console.log('Adding the prompt text');
      var promptText = document.createElement('span');
      promptText.setAttribute('id', 'prompt-text');
      promptText.textContent = PROMPT_TEXT;

      container.appendChild(promptText);
    } else {
      console.log('Prompt Text already present. Not going to add it again.');
    }
  } else {
    console.log("There's already content; I won't add the prompt text.");
  }
};

//Add it initially
addPromptText();
//Add the prompt whenever the container loses focus (and there's nothing written)
container.onblur = addPromptText;
//Remove it once the container is in focus.
container.onfocus = removePromptText;

