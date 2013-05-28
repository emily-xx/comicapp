package comicapp.content

class Page {

	String name
	String content

	String url

	Date lastUpdated

	Integer linkOrder

    static constraints = {
		name(nullable: false, maxLength: 200)
		content(nullable: true)

		url(nullable: true)

		lastUpdated(nullable: false)
    }

	static mapping = {
		content type: 'text'
	}
}
