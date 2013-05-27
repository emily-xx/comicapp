package content

import people.Comment

class Comic {

	static hasMany = [comments: Comment]

	Date dateCreated
	Date lastUpdated

	String description

	static constraints = {
		comments(nullable: true)

		dateCreated(nullable: false)
		lastUpdated(nullable: false)
		
	}

	static mapping = {
		sort dateCreated
		comments sort: dateCreated
	}
}
