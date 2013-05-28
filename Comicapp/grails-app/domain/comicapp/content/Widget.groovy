package comicapp.content

class Widget {

	String title
	String html

	String side
	Integer boxOrder

    static constraints = {
		title(nullable: false, maxLength: 255)
		html(nullable: false)

		side(nullable: false, inList: ['left', 'right'])
		boxOrder(nullable: false, unique: ['side', 'boxOrder'])
    }

	static mapping = {
		html type: 'text'
	}
}
