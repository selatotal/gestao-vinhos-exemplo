$('.menu-drop').dropdown({
	transition: 'fade up'
})
$('.message .close').on('click', function() {
	$(this)
		.closest('.message')
		.transition('fade')
	;
});

$('select').dropdown()

$('.ui.radio.checkbox').checkbox();