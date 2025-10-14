const featureCards = document.querySelectorAll('.feature-card');

featureCards.forEach(card => {
  const video = card.querySelector('.hover-video'); // Get the video inside the card
  
  card.addEventListener('mouseenter', () => {
    console.log("Feature card hover started");
    video.play(); // Start the video when the card is hovered
  });

  card.addEventListener('mouseleave', () => {
    console.log("Feature card hover ended");
    video.pause(); // Pause the video when the hover ends
  });
});
