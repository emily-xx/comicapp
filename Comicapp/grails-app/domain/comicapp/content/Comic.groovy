package comicapp.content

import comicapp.people.Comment

class Comic {

	Date dateCreated
	Date lastUpdated

	String title
	String description

	static hasMany = [comments: Comment]

	static constraints = {
		comments(nullable: true)

		dateCreated(nullable: false)
		lastUpdated(nullable: false)

		title(nullable: false, maxLength: 255)
		description(nullable: true)
	}

	static mapping = {
		sort dateCreated
		description type: 'text'

		comments sort: dateCreated
	}

	static transients = ['comicImageUrl']

	String getComicImageUrl() {
		// TODO: Storage and link
	}
}
