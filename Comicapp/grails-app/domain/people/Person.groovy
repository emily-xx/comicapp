package people

class Person {

	static hasMany = [comments: Comment]

	String fullName
	String email

	static constraints = {
		comments(nullable: true)

		fullName(nullable: false, blank: false)
		email(nullable: false, blank: false, email: true)
	}
}
