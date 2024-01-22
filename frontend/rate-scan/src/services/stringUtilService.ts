export const formatStringToLanguage = (text: String) => {
  text = text.replace('_', ' ')

  let op = text
    .toLowerCase()
    .replace(/^([a-z])|\ \s*([a-z])/g, (match) => match.toUpperCase())
  console.log(op)
  return op
}
