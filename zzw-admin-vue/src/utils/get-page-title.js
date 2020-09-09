import defaultSettings from '@/settings'

const title = defaultSettings.title || 'Welcome To Juan'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
